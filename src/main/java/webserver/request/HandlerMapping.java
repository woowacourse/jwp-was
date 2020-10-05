package webserver.request;

import controller.Controllers;
import webserver.response.HttpResponse;

import java.util.Arrays;

public enum HandlerMapping {
    PAGE(".html", Controllers.PAGE_CONTROLLER),
    GET_CSS(".css", Controllers.STATIC_RESOURCE_CONTROLLER),
    USER_CREATE("/user/create", Controllers.USER_CONTROLLER);

    private final String path;
    private final Controllers controllers;

    HandlerMapping(String path, Controllers controllers) {
        this.path = path;
        this.controllers = controllers;
    }

    public static HandlerMapping from(HttpRequest httpRequest) {
        return Arrays.stream(values())
                .filter(handlerMapping -> httpRequest.containsPath(handlerMapping.path))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 컨트롤러를 찾지 못했습니다." + httpRequest.getPath()));
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.controllers.service(httpRequest, httpResponse);
    }
}
