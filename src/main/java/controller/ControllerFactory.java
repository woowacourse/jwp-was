package controller;

import controller.creator.ControllerCreator;
import controller.creator.GetFileControllerCreator;
import controller.creator.PostUserControllerCreator;
import http.request.Request;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {

    private Map<String, ControllerCreator> controllerCreators;

    private void initialize() {
        controllerCreators = new HashMap<>();
        controllerCreators.put("POST /user/create", new PostUserControllerCreator());
        controllerCreators.put("FILE", new GetFileControllerCreator());
    }


    public Controller createController(Request request) {
        initialize();
        String key = request.createKey();

        String mapKey = controllerCreators.keySet()
                .stream()
                .filter(keyOfMap -> key.contains(keyOfMap))
                .findAny()
                .orElse("FILE");

        return controllerCreators.get(mapKey).createController(request);
    }
}

