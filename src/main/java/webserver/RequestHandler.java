package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.FileController;
import controller.HomeController;
import controller.LoginController;
import controller.UserListController;
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
        controllers.put(LoginController.PATH, new LoginController());
        controllers.put(UserListController.PATH, new UserListController());
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
            logger.debug("RequestLine: {} ", httpRequest.getHttpRequestLine());

            ResponseWriter.write(dataOutputStream, route(httpRequest));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse route(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();
        if (httpRequest.isContainExtension()) {
            FileController.getInstance().service(httpRequest, httpResponse);
            return httpResponse;
        }

        try {
            Optional.ofNullable(controllers.get(httpRequest.getUri()))
                    .orElseThrow(() -> new ControllerNotFoundException(httpRequest.getUri()))
                    .service(httpRequest, httpResponse);
        } catch (ControllerNotFoundException e) {
            httpResponse.setStatusCode(HttpStatus.NOT_FOUND);
        }
        return httpResponse;
    }
}
