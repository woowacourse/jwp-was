package webserver;

import controller.LoginController;
import controller.SignUpController;
import controller.UserListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ResourceLoadUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

    private static final String USER_CREATE_URL = "/user/create";
    private static final String USER_LOGIN_URL = "/user/login";
    private static final String USER_LIST_URL = "/user/list";

    private static final String MESSAGE_UNSUPPORTED_EXTENSION = "지원되지 않는 확장자 입니다.";
    private static final String EXTENSION_DELIMITER = "\\.";

    private static final Map<RequestMapping, Method> controllers;

    static {
        controllers = new HashMap<>();
        fillController(SignUpController.class, USER_CREATE_URL);
        fillController(LoginController.class, USER_LOGIN_URL);
        fillController(UserListController.class, USER_LIST_URL);
    }

    private static void fillController(Class className, String uri) {
        Method[] methods = className.getDeclaredMethods();
        Arrays.stream(methods)
                .forEach(method -> fillMethod(method, uri));
    }

    private static void fillMethod(Method method, String uri) {
        Optional<HttpMethod> httpMethod = HttpMethod.fromHandleMethodName(method.getName());
        httpMethod.ifPresent(value -> controllers.put(new RequestMapping(value, uri), method));
    }

    public static Response handle(Request request) {
        try {
            Optional<File> file = ResourceLoadUtils.detectFile(request.getRequestMapping().getUri());

            if (file.isPresent()) {
                return serveFile(file.get());
            }

            Method method = controllers.get(request.getRequestMapping());
            if (method != null) {
                logger.debug(method.getName());
                return (Response) method.invoke(method.getDeclaringClass(), request);
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
