package controller;

import controller.creator.ControllerCreator;
import controller.creator.GetFileControllerCreator;
import controller.creator.PostUserControllerCreator;
import http.request.Request;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {

    private Map<String, ControllerCreator> controller2Creators;

    private void initialize() {
        controller2Creators = new HashMap<>();
        controller2Creators.put("POST /user/create", new PostUserControllerCreator());
        controller2Creators.put("FILE", new GetFileControllerCreator());
    }


    public Controller createController(Request request) {
        initialize();
        String key = request.createKey();

        String mapKey = controller2Creators.keySet()
                .stream()
                .filter(keyOfMap -> key.contains(keyOfMap))
                .findAny()
                .orElse("FILE");

        return controller2Creators.get(mapKey).createController(request);
    }
}

