package webserver;

import controller.UserController;
import webserver.domain.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RequestDispatcher {
    private static final Map<String, Function<Request, byte[]>> requestUrls = new HashMap<>();

    static {
        requestUrls.put("/user/create", UserController::createUser);
    }

    public static byte[] forward(final Request request) {
        // TODO: 404 띄우기
        return requestUrls.getOrDefault(request.getPath(),
                (req) -> null).apply(request);
    }
}
