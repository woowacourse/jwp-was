package webserver;

import controller.UserController;
import utils.FileLoader;
import webserver.domain.request.Request;
import webserver.domain.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RequestDispatcher {
    private static final Map<String, Function<Request, Response>> requestUrls = new HashMap<>();

    static {
        requestUrls.put("/user/create", UserController::createUser);
    }

    public static Response forward(final Request request) {
        return requestUrls.getOrDefault(request.getPath(), FileLoader::serveStaticFile).apply(request);
    }
}
