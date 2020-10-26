package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.login.LoginController;
import webserver.user.UserController;

public enum Controllers {
    USER_CONTROLLER(new UserController()),
    STATIC_FILE_CONTROLLER(new StaticFileController()),
    LOGIN_CONTROLLER(new LoginController());

    private final Controller controller;

    Controllers(Controller controller) {
        this.controller = controller;
    }

    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        controller.doService(httpRequest, httpResponse);
    }
}
