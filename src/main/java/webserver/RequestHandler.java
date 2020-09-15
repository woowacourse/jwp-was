package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.StaticController;
import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;
import webserver.domain.url.UrlMapper;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(in, StandardCharsets.UTF_8)))) {

            HttpRequest httpRequest = new HttpRequest(bufferedReader);
            HttpResponse httpResponse = new HttpResponse(out);

            if (httpRequest.hasPathOfStaticFile()) {
                new StaticController().service(httpRequest, httpResponse);
            } else {
                Controller controller = UrlMapper.findMatchingController(httpRequest.getPath());
                controller.service(httpRequest, httpResponse);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
