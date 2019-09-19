package webserver;

import controller.UserController;
import webserver.domain.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RequestDispatcher {
    private static final Map<String, Function<Request, byte[]>> requestUrls = new HashMap<>();

    static {
        requestUrls.put("/create", UserController::createUser);
    }

    public static byte[] forward(final Request request) {
        return requestUrls.get(request.getPath()).apply(request);
    }
}
