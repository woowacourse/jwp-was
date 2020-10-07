package http.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllersFactory {
    private final Controllers controllers;
    private static final ControllersFactory controllersFactory = new ControllersFactory();

    public ControllersFactory() {
        Map<String, Controller> controllers = new HashMap<>();
        controllers.put("/user/create", new UserCreateController());
        controllers.put("/", new IndexController());
        controllers.put("/user/login", new UserLoginController());
        this.controllers = new Controllers(controllers);
    }

    public static Controllers getControllers() {
        return controllersFactory.controllers;
    }
}
