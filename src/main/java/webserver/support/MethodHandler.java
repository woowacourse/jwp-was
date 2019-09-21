package webserver.support;

import http.Request;
import http.Response;
import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.FileController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MethodHandler {
    private Map<String, Controller> api;

    public MethodHandler() {
        initialize();
    }

    private void initialize() {
        api = new HashMap<>();
        api.put("/user/create", new CreateUserController());
    }

    public void handle(Request request, Response response) throws IOException, URISyntaxException {
        Controller controller = Optional.ofNullable(api.get(request.extractUrl())).orElseGet(FileController::new);
        controller.service(request, response);
    }
}
