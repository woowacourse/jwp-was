package webserver.httprequesthandler;

import fileloader.StaticFileLoader;
import webserver.exception.HandlerNotFoundException;

import java.util.Arrays;
import java.util.List;

public class HandlerMapping {
    private static final List<HttpRequestHandler> HANDLERS = Arrays.asList(
            HttpServletRequestHandler.getInstance(),
            new HttpResourceRequestHandler(StaticFileLoader.getInstance())
    );

    public static HandlerMapping getInstance() {
        return HandlerMappingLazyHolder.INSTANCE;
    }

    public HttpRequestHandler getHandler(String path) {
        return HANDLERS.stream()
                .filter(httpRequestHandler -> httpRequestHandler.canHandle(path))
                .findAny()
                .orElseThrow(HandlerNotFoundException::new);
    }

    private static class HandlerMappingLazyHolder {
        private static final HandlerMapping INSTANCE = new HandlerMapping();
    }
}
