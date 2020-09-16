package webserver;

import http.HttpMethod;
import http.HttpRequest;

public class RequestMapping {
    private final String path;
    private final HttpMethod httpMethod;

    public RequestMapping(final String path, final HttpMethod httpMethod) {
        this.path = path;
        this.httpMethod = httpMethod;
    }

    public boolean matches(final HttpRequest httpRequest) {
        return httpRequest.equalsPath(path) && httpRequest.equalsMethod(httpMethod);
    }
}
