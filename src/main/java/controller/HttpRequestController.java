package controller;

import java.util.HashSet;
import java.util.Set;

import http.request.HttpRequest;
import view.ModelAndView;

public class HttpRequestController {

    private final Controller defaultController;
    private final Set<Controller> controllers = new HashSet<>();

    public HttpRequestController(Controller defaultController) {
        this.defaultController = defaultController;
    }

    public void addController(Controller controller) {
        controllers.add(controller);
    }

    public ModelAndView doService(HttpRequest httpRequest) {
        Controller selectedController = controllers.stream()
            .filter(controller -> controller.canHandle(httpRequest))
            .findAny()
            .orElse(defaultController);

        return selectedController.handle(httpRequest);
    }
}
