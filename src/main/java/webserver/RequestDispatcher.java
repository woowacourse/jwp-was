package webserver;

import controller.LoginController;
import controller.SignUpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.util.HashMap;
import java.util.Map;


public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);
    private static final String TEMPLATES_DIR = "./templates";
    private static final String STATIC_DIR = "./static";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String MESSAGE_UNSUPPORTED_EXTENSION = "지원되지 않는 확장자 입니다.";
    private static final String EXTENSION_DELIMITER = "\\.";

    private static final Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        SignUpController signUpController = new SignUpController();
        LoginController loginController = new LoginController();

        controllers.put(signUpController.getPath(), signUpController);
        controllers.put(loginController.getPath(), loginController);
    }

    public static void handle(HttpRequest request, HttpResponse response) {
        try {
            String url = request.getUrl();

            serveFile(STATIC_DIR + url, response);
            serveFile(TEMPLATES_DIR + url, response);

            Controller toServe = controllers.get(request.getPath());
            if (toServe != null) {
                toServe.service(request, response);
            }
        } catch (Exception e) {
            logger.error("Error is occurred while processing request", e);
        }
        if (response.getStatus() == null) {
            response.setStatus(HttpStatus.NOT_FOUND);
        }
    }

    private static void serveFile(String url, HttpResponse res) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(url);
            MediaType contentType = extractExtension(url);

            res.setStatus(HttpStatus.OK);
            res.setContentType(contentType);
            res.addHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
            res.setBody(body);
        } catch (Exception e) {
            logger.error("Error while serving file", e);
        }
    }

    private static MediaType extractExtension(String url) {
        String[] tokens = url.split(EXTENSION_DELIMITER);
        return MediaType.fromExtension(tokens[tokens.length - 1])
            .orElseThrow(() -> new IllegalArgumentException(MESSAGE_UNSUPPORTED_EXTENSION));
    }
}
