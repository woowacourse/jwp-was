package webserver;

import controller.Controller;
import http.HttpRequest;

public interface HandlerMapping {

    boolean matches(final HttpRequest httpRequest);

    Controller getController();
}
