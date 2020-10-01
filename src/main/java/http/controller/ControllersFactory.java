package http.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllersFactory {
    private final Controllers controllers;
    private static ControllersFactory controllersFactory;

    public ControllersFactory() {
        Map<String, Controller> controllers = new HashMap<>();
        controllers.put("/user/create", new UserCreateController());
        controllers.put("/", new IndexController());
        this.controllers = new Controllers(controllers);
    }

    public static Controllers getControllers() {
        return getInstance().controllers;
    }

    public static ControllersFactory getInstance() {
        if (controllersFactory == null) {
            controllersFactory = new ControllersFactory();
        }
        return controllersFactory;
    }
}
