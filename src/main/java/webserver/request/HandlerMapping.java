package webserver.request;

import controller.ControllerType;
import webserver.response.HttpResponse;

import java.util.Arrays;
import java.util.List;

public enum HandlerMapping {
    TEMPLATES(Arrays.asList(".html", ".ico"), ControllerType.TEMPLATES_CONTROLLER),
    STATIC(Arrays.asList("/css", "/js", "/fonts"), ControllerType.STATIC_RESOURCE_CONTROLLER),
    USER_CREATE(Arrays.asList("/user/create"), ControllerType.USER_CREATE_CONTROLLER),
    USER_LOGIN(Arrays.asList("/user/setCookieAndRedirect"), ControllerType.USER_LOGIN_CONTROLLER),
    USER_LIST(Arrays.asList("/user/list"), ControllerType.USER_LIST_CONTROLLER);

    private final List<String> paths;
    private final ControllerType controllerType;

    HandlerMapping(List<String> paths, ControllerType controllerType) {
        this.paths = paths;
        this.controllerType = controllerType;
    }

    public static HandlerMapping from(HttpRequest httpRequest) {
        return Arrays.stream(values())
                .filter(handlerMapping -> httpRequest.containsPath(handlerMapping.paths))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 컨트롤러를 찾지 못했습니다." + httpRequest.getPath()));
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.controllerType.service(httpRequest, httpResponse);
    }
}
