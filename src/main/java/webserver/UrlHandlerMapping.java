package webserver;

import http.HttpMethod;
import http.HttpRequest;

public class UrlHandlerMapping implements HandlerMapping {

    private final String path;
    private final HttpMethod httpMethod;
    private final Handler handler;

    public UrlHandlerMapping(final String path, final HttpMethod httpMethod, final Handler handler) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.handler = handler;
    }

    @Override
    public boolean matches(final HttpRequest httpRequest) {
        return httpRequest.equalsPath(path) && httpRequest.equalsMethod(httpMethod);
    }

    @Override
    public Handler getHandler() {
        return handler;
    }
}
