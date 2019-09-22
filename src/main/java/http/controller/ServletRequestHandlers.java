package http.controller;

import http.exceptions.IllegalRequestMappingException;
import http.model.ServletRequest;
import http.model.ServletResponse;

import java.util.HashSet;
import java.util.Set;

public class ServletRequestHandlers {
    private Set<Handler> handlers;
    private Handler defaultHandler;

    public ServletRequestHandlers(Handler defaultHandler) {
        this.handlers = new HashSet<>();
        this.defaultHandler = defaultHandler;
    }

    public void addHandler(Handler handler) {
        if (handlers.contains(handler)) {
            throw new IllegalRequestMappingException();
        }
        handlers.add(handler);
    }

    public void doService(ServletRequest request, ServletResponse response) {
        resolveRequestMapping(request).handle(request, response);
    }

    private Handler resolveRequestMapping(ServletRequest request) {
        return handlers.stream()
                .filter(controller -> controller.canHandle(request))
                .findAny()
                .orElse(this.defaultHandler);
    }
}
