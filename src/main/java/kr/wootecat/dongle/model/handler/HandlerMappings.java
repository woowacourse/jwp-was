package kr.wootecat.dongle.model.handler;

import java.util.List;

import kr.wootecat.dongle.model.http.exception.HandlerNotFoundException;
import kr.wootecat.dongle.model.http.request.HttpRequest;

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
