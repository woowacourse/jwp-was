package server.web;

import server.web.controller.Controller;
import server.web.request.RequestMapping;

public class Handler {
    private final RequestMapping requestMapping;
    private final Controller controller;

    public Handler(RequestMapping requestMapping, Controller controller) {
        this.requestMapping = requestMapping;
        this.controller = controller;
    }

    public RequestMapping getRequestMapping() {
        return requestMapping;
    }

    public Controller getController() {
        return controller;
    }
}
