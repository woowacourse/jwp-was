package web;

import client.controller.LoginController;
import client.controller.ResourceController;
import client.controller.UserCreateController;
import utils.ResourcePathExtractor;
import web.controller.Controller;
import web.request.HttpMethod;
import web.request.HttpRequest;
import web.request.RequestMapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class HandlerMapping {
    private static final Map<RequestMapping, Controller> handlerMapping = new HashMap<>();

    static {
        handlerMapping.put(new RequestMapping("/user/create", HttpMethod.POST), new UserCreateController());
        handlerMapping.put(new RequestMapping("/user/login", HttpMethod.POST), new LoginController());

        initResourceMapping();
    }

    private static void initResourceMapping() {
        Stream.of("./static", "./templates")
                .map(ResourcePathExtractor::extract)
                .flatMap(Collection::stream)
                .forEach(resourcePath -> handlerMapping.put(new RequestMapping(resourcePath.getRequestPath(), HttpMethod.GET),
                        new ResourceController(resourcePath.getFilePath())));
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
