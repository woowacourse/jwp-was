package http.controller;

import http.exceptions.IllegalRequestMappingException;
import http.model.HttpRequest;
import http.view.ModelAndView;

import java.util.HashSet;
import java.util.Set;

public class HttpRequestHandlers {
    private Set<Controller> controllers;
    private Controller defaultController;

    public HttpRequestHandlers(Controller defaultController) {
        controllers = new HashSet<>();
        this.defaultController = defaultController;
    }

    public void addHandler(Controller handler) {
        if (controllers.contains(handler)) {
            throw new IllegalRequestMappingException();
        }
        controllers.add(handler);
    }

    public ModelAndView doService(HttpRequest httpRequest) {
        return resolveRequestMapping(httpRequest).handle(httpRequest);
    }

    private Controller resolveRequestMapping(HttpRequest httpRequest) {
        return controllers.stream()
                .filter(controller -> controller.canHandle(httpRequest))
                .findAny()
                .orElse(this.defaultController);
    }
}
