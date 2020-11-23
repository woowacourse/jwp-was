package controller;

import application.controller.UserController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ControllerManager {

    private static final Map<Class<?>, Controller> controllers;

    static {
        Map<Class<?>, Controller> controllersMap = new HashMap<>();

        controllersMap.put(StaticFileController.class, new StaticFileController());
        controllersMap.put(UserController.class, new UserController());

        controllers = Collections.unmodifiableMap(controllersMap);
    }

    public static Controller get(Class<?> controllerClass) {
        if (!controllers.containsKey(controllerClass)) {
            throw new IllegalArgumentException("this controller does not exist.");
        }
        return controllers.get(controllerClass);
    }
}
