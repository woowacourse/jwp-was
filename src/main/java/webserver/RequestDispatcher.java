package webserver;

import controller.UserController;
import webserver.domain.Request;
import webserver.domain.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class RequestDispatcher {
    private static final Map<String, Function<Request, Response>> requestUrls = new HashMap<>();

    static {
        requestUrls.put("/user/create", UserController::createUser);
        requestUrls.put("/user/login", UserController::login);
        requestUrls.put("/user/list", UserController::userList);
    }

    static Response forward(final Request request) {
        return requestUrls.getOrDefault(request.getPath(), StaticFileServer::get).apply(request);
    }
}
