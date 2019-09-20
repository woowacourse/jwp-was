package http.controller;

import http.model.HttpRequest;
import http.view.ModelAndView;

import java.util.ArrayList;
import java.util.List;

public class HttpRequestHandlers {
    private List<Controller> controllers;
    private Controller defaultController;

    public HttpRequestHandlers(Controller defaultController) {
        controllers = new ArrayList<>();
        this.defaultController = defaultController;
    }

    public void addHandler(Controller handler) {
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
