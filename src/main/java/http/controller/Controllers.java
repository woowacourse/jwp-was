package http.controller;

import java.util.Map;

public class Controllers {
    private final Map<String, Controller> controllers;

    public Controllers(Map<String, Controller> controllers) {
        this.controllers = controllers;
    }

    public Controller find(String path) {
        return this.controllers.getOrDefault(path, new RawFileController(path));
    }
}
