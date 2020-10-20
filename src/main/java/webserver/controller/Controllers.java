package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public enum Controllers {
    USER_CONTROLLER(new UserController()),
    STATIC_FILE_CONTROLLER(new StaticFileController());

    private final Controller controller;

    Controllers(Controller controller) {
        this.controller = controller;
    }

    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        controller.doService(httpRequest, httpResponse);
    }
}
