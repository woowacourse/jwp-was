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

public class DefaultHandlerMapping implements HandlerMapping {
    private final List<Class<? extends Handlers>> handlers;

    public DefaultHandlerMapping(List<Class<? extends Handlers>> handlers) {
        this.handlers = handlers.stream()
            .filter(handler -> Objects.nonNull(handler.getAnnotation(Controller.class)))
            .collect(Collectors.toList());
    }

    public Method mapping(ServletRequest request) {
        MethodType methodType = request.getMethod();
        String path = request.getPath();

        return handlers.stream()
            .flatMap(controller -> Stream.of(controller.getMethods()))
            .filter(method -> {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                return Objects.nonNull(annotation)
                    && (isStaticResourceHandler(request, annotation)
                    || isMatchHandler(path, methodType, annotation));

            })
            .findAny()
            .orElseThrow(MethodNotAllowedException::new);
    }

    private boolean isStaticResourceHandler(ServletRequest request, RequestMapping annotation) {
        return request.hasStaticResource() && annotation.type().equals(MethodType.GET) && annotation.isResource();
    }

    private boolean isMatchHandler(String path, MethodType methodType, RequestMapping annotation) {
        return Arrays.asList(annotation.value()).contains(path) && annotation.type().equals(methodType);
    }
}
