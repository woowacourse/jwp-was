package webserver;

import java.util.List;

import webserver.http.request.HttpRequest;

public class HandlerMappings {
    private final List<HandlerMapping> handlers;

    public HandlerMappings(List<HandlerMapping> handlers) {
        this.handlers = handlers;
    }

    public HandlerMapping findHandler(HttpRequest request) {
        for (HandlerMapping handler : handlers) {
            if (handler.isSupport(request)) {
                return handler;
            }
        }
        return null;
    }
}
