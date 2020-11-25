package was.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.webserver.controller.Controller;
import was.webserver.http.request.HttpRequest;
import was.webserver.http.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final List<Controller> controllers;

    public RequestHandler(Socket connectionSocket, List<Controller> controllers) {
        this.connection = connectionSocket;
        this.controllers = controllers;
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);
            HandlerMapper handlerMapper = new HandlerMapper(controllers);
            handlerMapper.handle(httpRequest, httpResponse);
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("error message >> {}", e.getMessage(), e);
        }
    }
}
