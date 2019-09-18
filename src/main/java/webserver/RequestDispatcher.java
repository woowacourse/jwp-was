package webserver;

import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.util.HashMap;
import java.util.Map;

import static controller.UserController.USER_CREATE_URL;


public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);
    private static final String TEMPLATES_DIR = "./templates";
    private static final String STATIC_DIR = "./static";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String MESSAGE_UNSUPPORTED_EXTENSION = "지원되지 않는 확장자 입니다.";
    private static final String EXTENSION_DELIMITER = "\\.";

    public static Response handle(Request request) {
        try {
            String url = request.getUrl();
            Response response = serveFile(STATIC_DIR + url);
            if (response != null) {
                return response;
            }

            response = serveFile(TEMPLATES_DIR + url);
            if (response != null) {
                return response;
            }

            if (USER_CREATE_URL.equals(url)) {
                return UserController.signUp(request);
            }
        } catch (Exception e) {
            logger.error("Error is occurred while processing request", e);
        }
        return Response.ResponseBuilder.createBuilder()
            .withStatus(Status.NOT_FOUND)
            .build();
    }

    private static Response serveFile(String url) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(url);
            Map<String, String> headers = new HashMap<>();

            MediaType contentType = extractExtension(url);
            headers.put(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));

            return Response.ResponseBuilder.createBuilder()
                .withStatus(Status.OK)
                .withMediaType(contentType)
                .withHeaders(headers)
                .withBody(body)
                .build();
        } catch (Exception e) {
            return null;
        }
    }

    private static MediaType extractExtension(String url) {
        String[] tokens = url.split(EXTENSION_DELIMITER);
        return MediaType.fromExtension(tokens[tokens.length - 1])
            .orElseThrow(() -> new IllegalArgumentException(MESSAGE_UNSUPPORTED_EXTENSION));
    }
}
