package webserver;

import controller.Controller;
import controller.ControllerFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestParser;
import webserver.response.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = HttpRequestParser.parseHttpRequest(br);

            String uri = httpRequest.findUri();
            Controller controller = ControllerFinder.findController(uri);

            HttpResponse httpResponse = new HttpResponse(out);

            controller.service(httpRequest, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
