package web;

import web.servlet.Controller;
import web.servlet.UserCreateController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HandlerMapping {
    private static final Map<RequestMapping, Controller> handlerMapping = new HashMap<>();

    static {
        handlerMapping.put(new RequestMapping("/user/create", HttpMethod.POST), new UserCreateController());
    }

    public static Controller find(HttpRequest httpRequest) {
        RequestMapping requestMapping = new RequestMapping(httpRequest.getRequestPath(), httpRequest.getMethod());
        Controller controller = handlerMapping.get(requestMapping);
        if (Objects.isNull(controller)) {
            throw new IllegalArgumentException(String.format("요청(%s: %s)을 처리할 수 없습니다.",
                    httpRequest.getMethod(), httpRequest.getRequestPath()));
        }
        return controller;
    }
}
