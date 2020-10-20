package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.Controller;
import controller.FileController;
import domain.user.service.UserService;
import domain.user.web.LoginController;
import domain.user.web.UserCreateController;
import domain.user.web.UserListController;
import domain.user.web.UserReadController;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Socket connection;
    private final Map<String, Controller> controllers;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        UserService userService = UserService.getInstance();

        controllers = new HashMap<>();
        controllers.put("FILE", new FileController());
        controllers.put("/user/create", new UserCreateController(userService));
        controllers.put("/user/profile", new UserReadController(userService, objectMapper));
        controllers.put("/user/login", new LoginController(userService));
        controllers.put("/user/list", new UserListController(userService, objectMapper));
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(br);
            printHeader(httpRequest);
            printParameter(httpRequest);
            Controller controller = controllers.getOrDefault(httpRequest.getPath(), controllers.get("FILE"));
            controller.service(httpRequest, new HttpResponse(out));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void printHeader(HttpRequest httpRequest) {
        logger.debug("header : {}",
            String.format("%s %s HTTP/1.1", httpRequest.getHttpMethod().name(), httpRequest.getPath()));
        httpRequest.getHeader()
            .forEach((key, value) -> logger.debug("header : {}", String.format("%s: %s", key, value)));
    }

    private void printParameter(HttpRequest httpRequest) {
        httpRequest.getParameter()
            .forEach((key, value) -> logger.debug("body : {}", String.format("%s = %s", key, value)));
    }
}
