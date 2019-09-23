package controller;

import controller.creator.ControllerCreator;
import controller.creator.FileControllerCreator;
import controller.creator.UserControllerCreator;
import http.request.Request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerFactory {

    private List<Controller> controllers = Arrays.asList(new UserController(), new FileController());

    public Controller createController(Request request) {
        return controllers.stream()
                .filter(controller -> controller.isMapping(request.createControllerMapper()))
                .findAny()
                .orElse(new ExceptionController());
    }

//    private Map<String, ControllerCreator> controllerCreators;
//
//    private void initialize() {
//        controllerCreators = new HashMap<>();
//        controllerCreators.put("POST /user/create", new UserControllerCreator());
//        controllerCreators.put("FILE", new FileControllerCreator());
//    }
//
//
//    public Controller createController(Request request) {
//        initialize();
//        String key = request.createKey();
//
//        String mapKey = controllerCreators.keySet()
//                .stream()
//                .filter(keyOfMap -> key.contains(keyOfMap))
//                .findAny()
//                .orElse("FILE");
//
//        return controllerCreators.get(mapKey).createController(request);
//    }
}

