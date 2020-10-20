package webserver.request;

import controller.Controllers;
import webserver.response.HttpResponse;

import java.util.Arrays;
import java.util.List;

public enum HandlerMapping {
    TEMPLATES(Arrays.asList(".html", ".ico"), Controllers.TEMPLATES_CONTROLLER),
    STATIC(Arrays.asList("/css", "/js", "/fonts"), Controllers.STATIC_RESOURCE_CONTROLLER),
    USER_CREATE(Arrays.asList("/user/create"), Controllers.USER_CONTROLLER);

    private final List<String> paths;
    private final Controllers controllers;

    HandlerMapping(List<String> paths, Controllers controllers) {
        this.paths = paths;
        this.controllers = controllers;
    }

    public static HandlerMapping from(HttpRequest httpRequest) {
        return Arrays.stream(values())
                .filter(handlerMapping -> httpRequest.containsPath(handlerMapping.paths))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 컨트롤러를 찾지 못했습니다." + httpRequest.getPath()));
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.controllers.service(httpRequest, httpResponse);
    }
}
