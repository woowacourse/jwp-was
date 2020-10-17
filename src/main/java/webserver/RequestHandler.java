package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.HttpRequestController;
import controller.LoginController;
import controller.ResourceController;
import controller.UserCreateController;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import http.request.HttpRequestParser;
import http.response.HttpResponse;
import http.response.ResponseEntity;
import view.ModelAndView;
import view.ViewResolver;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final HttpRequestController httpRequestController;
    private final ViewResolver viewResolver;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.httpRequestController = setHttpRequestController();
        this.viewResolver = new ViewResolver();
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
            ModelAndView modelAndView = httpRequestController.doService(httpRequest);
            HttpResponse httpResponse = viewResolver.resolve(modelAndView);
            ResponseEntity.build(httpResponse, out);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
