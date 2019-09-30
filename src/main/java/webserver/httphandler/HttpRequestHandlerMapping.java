package webserver.httphandler;

import fileloader.ResourceFileLoader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.httphandler.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;

public class HttpRequestHandlerMapping {
    private static final List<HttpRequestHandler> HANDLERS = Arrays.asList(
            HttpServletRequestHandler.getInstance(),
            new HttpResourceRequestHandler(new ResourceFileLoader()));

    public static HttpRequestHandlerMapping getInstance() {
        return HandlerMappingLazyHolder.INSTANCE;
    }

    private static void throwResourceNotFound(HttpRequest req, HttpResponse res) {
        throw new ResourceNotFoundException();
    }

    public HttpRequestHandler getHandler(String path) {
        return HANDLERS.stream()
                .filter(httpRequestHandler -> httpRequestHandler.canHandle(path))
                .findAny()
                .orElse(HttpRequestHandlerMapping::throwResourceNotFound);
    }

    private static class HandlerMappingLazyHolder {
        private static final HttpRequestHandlerMapping INSTANCE = new HttpRequestHandlerMapping();
    }
}
