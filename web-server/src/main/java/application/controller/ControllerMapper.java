package application.controller;

import controller.ControllerMapperInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import request.HttpRequest;

public class ControllerMapper implements ControllerMapperInterface {

    private Map<String, Class> controllersByUriPath = new HashMap<>();
    private Class staticFileController = StaticFileController.class;

    public ControllerMapper() {
        controllersByUriPath.put(UserController.CREATE_URI_PATH, UserController.class);
        controllersByUriPath.put(UserController.LOGIN_URI_PATH, UserController.class);
        controllersByUriPath.put(UserController.FIND_USER_URI_PATH, UserController.class);
    }

    @Override
    public Class findController(HttpRequest httpRequest) {
        String uriPath = httpRequest.getUriPath();

        if (controllersByUriPath.containsKey(uriPath)) {
            return controllersByUriPath.get(uriPath);
        }
        return staticFileController;
    }

    @Override
    public List<Class> readAllControllerInfo() {
        List<Class> allControllers = new ArrayList<>(controllersByUriPath.values());
        allControllers.add(staticFileController);

        return Collections.unmodifiableList(allControllers);
    }
}
