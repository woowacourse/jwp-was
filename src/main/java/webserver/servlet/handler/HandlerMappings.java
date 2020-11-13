package webserver.servlet.handler;

import java.util.List;

import webserver.servlet.exception.HandlerNotFoundException;
import webserver.servlet.http.request.HttpRequest;

public class HandlerMappings {

    private final List<HandlerMapping> handlers;

    public HandlerMappings(List<HandlerMapping> handlers) {
        this.handlers = handlers;
    }

    public HandlerMapping findHandler(HttpRequest request) {
        return handlers.stream()
                .filter(handler -> handler.isSupport(request))
                .findAny()
                .orElseThrow(() -> new HandlerNotFoundException(request.getPath()));
    }
}
