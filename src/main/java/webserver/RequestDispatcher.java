package webserver;

import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ResourceLoadUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static controller.UserController.USER_CREATE_URL;

public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);
    private static final String MESSAGE_UNSUPPORTED_EXTENSION = "지원되지 않는 확장자 입니다.";
    private static final String EXTENSION_DELIMITER = "\\.";

    public static Response handle(Request request) {
        try {
            String url = request.getPath();
            Optional<File> file = ResourceLoadUtils.detectFile(url);

            if (file.isPresent()) {
                return serveFile(file.get());
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
