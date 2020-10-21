package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.controller.HttpRequestController;
import http.controller.LoginController;
import http.controller.ResourceController;
import http.controller.UserCreateController;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import http.request.HttpRequestParser;
import http.response.HttpResponse;
import http.response.ResponseEntity;
import http.session.HttpSessionManager;
import http.session.RandomGenerateStrategy;
import http.session.SessionManager;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final HttpRequestController httpRequestController;
    private final SessionManager sessionManager;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.httpRequestController = setHttpRequestController();
        this.sessionManager = new HttpSessionManager(new RandomGenerateStrategy());
    }

    private HttpRequestController setHttpRequestController() {
        HttpRequestController httpRequestController = new HttpRequestController(
            new ResourceController(HttpRequestMapping.GET("/")));
        UserCreateController userCreateController = new UserCreateController(HttpRequestMapping.POST("/user/create"));
        LoginController loginController = new LoginController(HttpRequestMapping.POST("/user/login"));

        httpRequestController.addController(userCreateController);
        httpRequestController.addController(loginController);
        return httpRequestController;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest httpRequest = HttpRequestParser.parse(in);
            httpRequest.setSessionManager(this.sessionManager);
            HttpResponse httpResponse = HttpResponse.from(out);
            httpRequestController.doService(httpRequest, httpResponse);
            ResponseEntity.build(httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
