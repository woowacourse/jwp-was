package web;

import client.controller.ResourceController;
import client.controller.UserCreateController;
import utils.ResourcePath;
import utils.ResourcePathExtractor;
import web.controller.Controller;
import web.request.HttpMethod;
import web.request.HttpRequest;
import web.request.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HandlerMapping {
    private static final Map<RequestMapping, Controller> handlerMapping = new HashMap<>();

    static {
        handlerMapping.put(new RequestMapping("/user/create", HttpMethod.POST), new UserCreateController());

        List<ResourcePath> resourcePaths = ResourcePathExtractor.extract("./static");
        resourcePaths.addAll(ResourcePathExtractor.extract("./templates"));

        for (ResourcePath resourcePath : resourcePaths) {
            handlerMapping.put(new RequestMapping(resourcePath.getRequestPath(), HttpMethod.GET), new ResourceController(resourcePath.getFilePath()));
        }
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
