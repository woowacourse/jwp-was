package webserver.request;

import controller.Controller;
import controller.PageController;
import controller.StaticResourceController;
import controller.UserController;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public enum HandlerMapping {
    PAGE(".html", new PageController()),
    GET_CSS(".css", new StaticResourceController()),
    USER_CREATE("/user/create", new UserController());

    private final String path;
    private final Controller controller;

    HandlerMapping(String path, Controller controller) {
        this.path = path;
        this.controller = controller;
    }

    public static HandlerMapping from(HttpRequest httpRequest) {
        return Arrays.stream(values())
                .filter(handlerMapping -> httpRequest.containsPath(handlerMapping.path))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 컨트롤러를 찾지 못했습니다." + httpRequest.getPath()));
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        this.controller.service(httpRequest, httpResponse);
    }
}
