package web.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.application.FrontController;
import web.application.controller.StaticController;
import web.application.vo.RequestVo;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

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
                StaticController.getInstance()
                    .service(httpRequest, httpResponse);
            } else {
                RequestVo requestVo = RequestVo.of(httpRequest.getPath(), httpRequest.getRequestMethod());
                BiConsumer<HttpRequest, HttpResponse> service = FrontController.findMatchingService(requestVo);
                service.accept(httpRequest, httpResponse);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
