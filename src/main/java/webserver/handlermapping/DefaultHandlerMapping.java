package webserver.handlermapping;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import exception.MethodNotAllowedException;
import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;
import webserver.request.ServletRequest;

public class DefaultHandlerMapping implements HandlerMapping {
    private final List<Class<?>> handlers;
    private final List<HandlerMappingStrategy> strategies;

    public DefaultHandlerMapping(List<HandlerMappingStrategy> strategies, List<Class<?>> handlers) {
        this.strategies = strategies;
        this.handlers = handlers.stream()
            .filter(handler -> Objects.nonNull(handler.getAnnotation(Controller.class)))
            .collect(Collectors.toList());
    }

    public Method mapping(ServletRequest request) {

        return handlers.stream()
            .flatMap(controller -> Stream.of(controller.getMethods()))
            .filter(method -> Objects.nonNull(method.getAnnotation(RequestMapping.class))
                && strategies.stream()
                .anyMatch(strategy -> strategy.isSupport(request, method.getAnnotation(RequestMapping.class))))
            .findAny()
            .orElseThrow(MethodNotAllowedException::new);
    }
}
