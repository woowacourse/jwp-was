package webserver.request;

import controller.FileController;
import domain.user.controller.UserController;
import webserver.response.HttpResponse;

import java.util.Arrays;
import java.util.function.Function;

public enum HandlerMapping {
    PAGE(HttpMethod.GET, ".html", FileController::getPage),
    GET_CSS(HttpMethod.GET, ".css", FileController::getCss),
    GET_USER_CREATE(HttpMethod.GET, "/user/create", UserController::getCreateUser),
    POST_USER_CREATE(HttpMethod.POST, "/user/create", UserController::postCreateUser);

    private final HttpMethod httpMethod;
    private final String path;
    private final Function<HttpRequest, HttpResponse> function;

    HandlerMapping(HttpMethod httpMethod, String path, Function<HttpRequest, HttpResponse> function) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.function = function;
    }

    public static HandlerMapping from(HttpRequest httpRequest) {
        return Arrays.stream(values())
                .filter(handlerMapping -> httpRequest.isMatchHttpMethod(handlerMapping.httpMethod))
                .filter(handlerMapping -> httpRequest.containsPath(handlerMapping.path))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 컨트롤러를 찾지 못했습니다." + httpRequest.getPath()));
    }

    public HttpResponse apply(HttpRequest httpRequest) {
        return this.function.apply(httpRequest);
    }
}
