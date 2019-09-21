package http.controllermapper;

import controller.Controller;
import controller.FileController;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapperRepository {
    private Map<String, ControllerMapper> controllerMappers;

    public static ControllerMapper getController(String mapper) {
        i
    }

    private void initialize() {
        controllerMappers = new HashMap<>();
        controllerMappers.put("GET .html", new FileController());

    }
}
