package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.FileController;
import controller.HomeController;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RequestParser;
import utils.ResponseWriter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put(CreateUserController.PATH, new CreateUserController());
        controllers.put(HomeController.PATH, new HomeController());
    }

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            HttpRequest httpRequest = RequestParser.parse(inputStream);
            HttpResponse httpResponse = new HttpResponse();
            logger.debug("RequestLine: {} ", httpRequest.getHttpRequestLine());

            route(httpRequest, httpResponse);

            ResponseWriter.write(dataOutputStream, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void route(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isContainExtension()) {
            FileController.getInstance().service(httpRequest, httpResponse);
            return;
        }

        try {
            Optional.ofNullable(controllers.get(httpRequest.getUri()))
                    .orElseThrow(() -> new ControllerNotFoundException(httpRequest.getUri()))
                    .service(httpRequest, httpResponse);
        } catch (ControllerNotFoundException e) {
            httpResponse.setStatusCode(HttpStatus.NOT_FOUND);
        }
    }
}
