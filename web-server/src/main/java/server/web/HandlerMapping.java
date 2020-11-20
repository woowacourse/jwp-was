package server.web;

import server.utils.HandlerFactory;
import server.utils.PathExtractor;
import server.web.controller.Controller;
import server.web.controller.ResourceController;
import server.web.request.HttpMethod;
import server.web.request.HttpRequest;
import server.web.request.RequestMapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class HandlerMapping {
    private final Map<RequestMapping, Controller> handler = new HashMap<>();

    public HandlerMapping(Class<?> rootApplication) {
        List<String> sourcePaths = PathExtractor.extractSourcePath(rootApplication);
        for (String sourcePath : sourcePaths) {
            if (HandlerFactory.isController(sourcePath)) {
                Handler handler = HandlerFactory.create(sourcePath);
                this.handler.put(handler.getRequestMapping(), handler.getController());
            }
        }
        initResourceMapping();
    }

    private void initResourceMapping() {
        Stream.of("static", "templates")
                .map(PathExtractor::extractResourcePath)
                .flatMap(Collection::stream)
                .forEach(resourcePath -> handler.put(new RequestMapping(resourcePath.getRequestPath(), HttpMethod.GET),
                        new ResourceController(resourcePath.getFilePath())));
    }

    public Controller find(HttpRequest httpRequest) {
        RequestMapping requestMapping = new RequestMapping(httpRequest.getRequestPath(), httpRequest.getMethod());
        Controller controller = handler.get(requestMapping);
        if (Objects.isNull(controller)) {
            throw new IllegalArgumentException(String.format("요청(%s: %s)을 처리할 수 없습니다.",
                    httpRequest.getMethod(), httpRequest.getRequestPath()));
        }
        return controller;
    }
}
