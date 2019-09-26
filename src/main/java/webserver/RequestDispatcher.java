package webserver;

import controller.Controller;
import controller.LoginController;
import controller.SignUpController;
import controller.UserListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ResourceLoadUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);
    private static final String MESSAGE_UNSUPPORTED_EXTENSION = "지원되지 않는 확장자 입니다.";
    private static final String EXTENSION_DELIMITER = "\\.";

    private static final Map<RequestMapping, Controller> controllers;

    static {
        controllers = new HashMap<>();
        SignUpController signUpController = new SignUpController();
        LoginController loginController = new LoginController();
        UserListController userListController = new UserListController();

        signUpController.getMethodKeys().forEach(requestMapping -> controllers.put(requestMapping, signUpController));
        loginController.getMethodKeys().forEach(requestMapping -> controllers.put(requestMapping, loginController));
        userListController.getMethodKeys().forEach(requestMapping -> controllers.put(requestMapping, userListController));
    }

    public static Response handle(Request request) {
        try {
            Optional<File> file = ResourceLoadUtils.detectFile(request.getRequestMapping().getUri());

            if (file.isPresent()) {
                return serveFile(file.get());
            }

            Controller toServe = controllers.get(request.getRequestMapping());
            if (toServe != null && toServe.isMapping(request)) {
                return toServe.service(request);
            }
        } catch (Exception e) {
            logger.error("Error is occurred while processing request", e);
        }
        return Response.ResponseBuilder.createBuilder()
                .withStatus(Status.NOT_FOUND)
                .build();
    }

    private static Response serveFile(File file) {
        try {
            return Response.ResponseBuilder.createBuilder()
                    .withStatus(Status.OK)
                    .withMediaType(extractExtension(file.getName()))
                    .withBody(Files.readAllBytes(file.toPath()))
                    .build();
        } catch (IOException e) {
            logger.error("serveFile error : {}", e.getMessage());
            return null;
        }
    }

    private static MediaType extractExtension(String url) {
        String[] tokens = url.split(EXTENSION_DELIMITER);
        return MediaType.fromExtension(tokens[tokens.length - 1])
                .orElseThrow(() -> new IllegalArgumentException(MESSAGE_UNSUPPORTED_EXTENSION));
    }
}
