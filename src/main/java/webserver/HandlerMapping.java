package webserver;

import http.HttpRequest;

public interface HandlerMapping {

    boolean matches(final HttpRequest httpRequest);

    Handler getHandler();
}
