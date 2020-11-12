package controller;

import java.util.HashSet;
import java.util.Set;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class HttpRequestController {

    private static Controller defaultController;
    private static final Set<Controller> controllers = new HashSet<>();

    private HttpRequestController() {
    }

    public static void addDefaultController(Controller controller) {
        defaultController = controller;
    }

    public static void addController(Controller controller) {
        controllers.add(controller);
    }

    public static void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller selectedController = controllers.stream()
            .filter(controller -> controller.canHandle(httpRequest))
            .findAny()
            .orElse(defaultController);

        selectedController.handle(httpRequest, httpResponse);
    }
}
