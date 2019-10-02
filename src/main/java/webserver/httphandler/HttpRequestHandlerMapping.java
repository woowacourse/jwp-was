package webserver.httphandler;

import fileloader.ResourceFileLoader;

import java.util.Arrays;
import java.util.List;

public class HttpRequestHandlerMapping {
    private static final HttpRequestHandler RESOURCE_REQUEST_HANDLER = new HttpResourceRequestHandler(new ResourceFileLoader());
    private static final List<HttpRequestHandler> HANDLERS = Arrays.asList(
            HttpServletRequestHandler.getInstance(), RESOURCE_REQUEST_HANDLER);

    public static HttpRequestHandlerMapping getInstance() {
        return HandlerMappingLazyHolder.INSTANCE;
    }

    public HttpRequestHandler getHandler(String path) {
        return HANDLERS.stream()
                .filter(httpRequestHandler -> httpRequestHandler.canHandle(path))
                .findAny()
                .orElse(RESOURCE_REQUEST_HANDLER);
    }

    private static class HandlerMappingLazyHolder {
        private static final HttpRequestHandlerMapping INSTANCE = new HttpRequestHandlerMapping();
    }
}
