package webserver;

import controller.Controller;
import controller.UserController;
import http.request.HttpRequest;
import http.request.HttpRequestCreator;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final UserController userController = UserController.getInstance();
    private final ResourceHttpRequestHandler resourceHttpRequestHandler = ResourceHttpRequestHandler.getInstance();
    private final HandlerMapping handlerMapping = HandlerMapping.getInstance();
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestCreator.create(inputStream);
            HttpResponse httpResponse = new HttpResponse();

            handleRequest(httpRequest, httpResponse);
            sendResponse(outputStream, httpResponse);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (resourceHttpRequestHandler.canHandle(httpRequest)) {
            resourceHttpRequestHandler.handleHttpRequest(httpRequest, httpResponse);
            return;
        }

        Controller controller = handlerMapping.getHandler(httpRequest.getPath());
        controller.service(httpRequest, httpResponse);
    }

    private void sendResponse(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);

        try {
            byte[] response = httpResponse.serialize();
            dos.write(response, 0, response.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
