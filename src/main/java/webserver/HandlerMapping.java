package webserver;

import controller.PageController;
import controller.UserController;

import java.util.Arrays;
import java.util.function.Function;

public enum HandlerMapping {
    GET_PAGE(new RequestLine(HttpMethod.GET, ".html"), PageController::getPagePath),
    GET_USER_CREATE(new RequestLine(HttpMethod.GET, "/user/create"), UserController::createUser);

    private final RequestLine requestLine;
    private final Function<Request, String> function;

    HandlerMapping(RequestLine requestLine, Function<Request, String> function) {
        this.requestLine = requestLine;
        this.function = function;
    }

    public static HandlerMapping from(Request request) {
        return Arrays.stream(values())
                .filter(handlerMapping -> request.isMatchRequestLine(handlerMapping.requestLine))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 컨트롤러를 찾지 못했습니다." + request.getResource()));
    }


    public String apply(Request request) {
        return this.function.apply(request);
    }
}
