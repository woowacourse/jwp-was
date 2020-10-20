package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public enum Controllers {
    TEMPLATES_CONTROLLER(new TemplatesController()),
    STATIC_RESOURCE_CONTROLLER(new StaticResourceController()),
    USER_CONTROLLER(new UserController());

    private final Controller controller;

    Controllers(Controller controller) {
        this.controller = controller;
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.controller.service(httpRequest, httpResponse);
    }
}
