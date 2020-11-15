package kr.wootecat.dongle.core.handler;

import java.util.List;

import kr.wootecat.dongle.http.exception.HandlerNotFoundException;
import kr.wootecat.dongle.http.request.HttpRequest;

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
