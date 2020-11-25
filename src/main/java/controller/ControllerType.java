package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public enum ControllerType {
    TEMPLATES_CONTROLLER(new TemplatesController()),
    STATIC_RESOURCE_CONTROLLER(new StaticResourceController()),
    USER_CREATE_CONTROLLER(new UserCreateController()),
    USER_LOGIN_CONTROLLER(new UserLoginController()),
    USER_LIST_CONTROLLER(new UserListController());

    private final Controller controller;

    ControllerType(Controller controller) {
        this.controller = controller;
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.controller.service(httpRequest, httpResponse);
    }
}
