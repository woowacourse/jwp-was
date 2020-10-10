package webserver;

import controller.Controller;
import http.HttpRequest;

public class UrlHandlerMapping implements HandlerMapping {

    private final String path;
    private final Controller controller;

    public UrlHandlerMapping(final String path, final Controller controller) {
        this.path = path;
        this.controller = controller;
    }

    @Override
    public boolean matches(final HttpRequest httpRequest) {
        return httpRequest.equalsPath(path);
    }

    @Override
    public Controller getController() {
        return controller;
    }
}
