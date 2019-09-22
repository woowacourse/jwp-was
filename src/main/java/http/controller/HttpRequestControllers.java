package http.controller;

import http.exceptions.IllegalRequestMappingException;
import http.model.ServletRequest;
import http.model.ServletResponse;

import java.util.HashSet;
import java.util.Set;

public class HttpRequestControllers {
    private Set<Controller> controllers;
    private Controller defaultController;

    public HttpRequestControllers(Controller defaultController) {
        this.controllers = new HashSet<>();
        this.defaultController = defaultController;
    }

    public void addHandler(Controller controller) {
        if (controllers.contains(controller)) {
            throw new IllegalRequestMappingException();
        }
        controllers.add(controller);
    }

    public void doService(ServletRequest request, ServletResponse response) {
        resolveRequestMapping(request).handle(request, response);
    }

    private Controller resolveRequestMapping(ServletRequest request) {
        return controllers.stream()
                .filter(controller -> controller.canHandle(request))
                .findAny()
                .orElse(this.defaultController);
    }
}
