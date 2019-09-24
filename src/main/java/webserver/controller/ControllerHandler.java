package webserver.controller;

import webserver.exception.InternalServerException;
import webserver.exception.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ControllerHandler {
    private Map<RequestMapping, Responsive> controllerMapping;

    public ControllerHandler() {
        this.controllerMapping = new HashMap<>();
    }

    public void put(RequestMapping requestMapping, Responsive responsive) {
        controllerMapping.put(requestMapping, responsive);
    }

    public boolean containsKey(RequestMapping requestMapping) {
        return controllerMapping.containsKey(requestMapping);
    }

    public BiConsumer<HttpRequest, HttpResponse> get(RequestMapping requestMapping) {
        if (containsKey(requestMapping)) {
            return controllerMapping.get(requestMapping);
        }
        if (containsUri(requestMapping)) {
            throw new MethodNotAllowedException("지원하지 않는 method");
        }
        throw new InternalServerException("잘 못된 요청");
    }

    private boolean containsUri(RequestMapping requestMapping) {
        return controllerMapping.keySet().stream()
                .anyMatch(rm -> rm.isSameUri(requestMapping));
    }
}
