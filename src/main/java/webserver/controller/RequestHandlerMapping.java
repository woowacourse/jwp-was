package webserver.controller;

import java.util.Map;
import java.util.Objects;
import webserver.FileExtension;

public class RequestHandlerMapping {

    private final Map<String, Controller> handlerMapping;

    public RequestHandlerMapping(Map<String, Controller> handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public void putController(String uri, Controller controller) {
        handlerMapping.put(uri, controller);
    }

    public Controller getController(String uri) {
        Controller controller = handlerMapping.get(uri);

        if (Objects.isNull(controller)) {
            if (FileExtension.isFileExtension(uri) ) {
                return DefaultController.getInstance();
            }
            return ErrorController.getInstance();
        }
        return controller;
    }
}
