package webserver;

import controller.UserController;
import webserver.domain.HandlerKey;
import webserver.domain.HttpMethod;
import webserver.domain.Request;
import webserver.domain.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class RequestDispatcher {
    private static final Map<HandlerKey, Function<Request, Response>> requestUrls = new HashMap<>();
    private static final StaticFileServer STATIC_FILE_SERVER = new StaticFileServer();

    static {
        requestUrls.put(new HandlerKey("/user/create", HttpMethod.POST), UserController::createUser);
        requestUrls.put(new HandlerKey("/user/login", HttpMethod.POST), UserController::login);
        requestUrls.put(new HandlerKey("/user/list", HttpMethod.GET), UserController::userList);
    }

    static Response forward(final Request request) {
        final HandlerKey handlerKey = new HandlerKey(request.getPath(), request.getHttpMethod());
        return requestUrls.getOrDefault(handlerKey, STATIC_FILE_SERVER::get).apply(request);
    }
}
