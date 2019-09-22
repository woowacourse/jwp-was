package controller;

import controller.creator.*;
import http.request.Request2;

import java.util.HashMap;
import java.util.Map;

public class Controller2Factory {

    private Map<String, Controller2Creator> controller2Creators;

    private void initialize() {
        controller2Creators = new HashMap<>();
        controller2Creators.put("POST /user/create", new PostUserControllerCreator());
        controller2Creators.put("FILE", new GetFileControllerCreator());
//        controller2Creators.put("GET /user", new GetFileControllerCreator());
//        controller2Creators.put("GET /html", new GetFileControllerCreator());
//        controller2Creators.put("POST /html", new GetFileControllerCreator());
//        controller2Creators.put("GET /css", new GetFileControllerCreator());
//        controller2Creators.put("GET /js", new GetFileControllerCreator());
//        controller2Creators.put("GET /ico", new GetFileControllerCreator());
//        controller2Creators.put("GET /woff", new GetFileControllerCreator());
//        controller2Creators.put("GET /ttf", new GetFileControllerCreator());
    }


    public Controller2 createController(Request2 request) {
        initialize();
        String key = request.createKey();
//        String mapKey = controller2Creators.keySet()
//                .stream()
//                .filter(keyOfMap -> key.contains(keyOfMap))
//                .findFirst()
//                .orElseThrow(IllegalArgumentException::new);
        String mapKey = controller2Creators.keySet()
                .stream()
                .filter(keyOfMap -> key.contains(keyOfMap))
                .findAny()
                .orElse("FILE");

        return controller2Creators.get(mapKey).createController(request);
    }

}

