package webserver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import exception.MethodNotAllowedException;
import webserver.controller.Handlers;
import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;

public class HandlerMapping {
    private final List<Class<? extends Handlers>> handlers;

    public HandlerMapping(List<Class<? extends Handlers>> handlers) {
        this.handlers = handlers.stream()
            .filter(handler -> Objects.nonNull(handler.getAnnotation(Controller.class)))
            .collect(Collectors.toList());
    }

    public Method mapping(ServletRequest request) {
        RequestHeader.MethodType methodType = request.getMethod();
        String path =
            request.getPath().endsWith(".html") ?
                request.getPath().substring(0, request.getPath().lastIndexOf("."))
                : request.getPath();

        return handlers.stream()
            .flatMap(controller -> Stream.of(controller.getMethods()))
            .filter(method -> {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                return Objects.nonNull(annotation) && Arrays.asList(annotation.value()).contains(path) && annotation.type().equals(methodType);
            })
            .findAny()
            .orElseThrow(MethodNotAllowedException::new);
    }
}
