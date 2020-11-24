package http.controller;

import java.util.HashMap;
import java.util.Map;

public class Controllers {
    private final Map<String, Controller> controllers;
    private final Map<String, Controller> rawFileControllers = new HashMap<>();

    public Controllers(Map<String, Controller> controllers) {
        this.controllers = controllers;
    }

    public Controller find(String path) {
        return this.controllers.getOrDefault(path, findRawFile(path));
    }

    private Controller findRawFile(String path) {
        if (!this.rawFileControllers.containsKey(path)) {
            this.rawFileControllers.put(path, new RawFileController(path));
        }
        return this.rawFileControllers.get(path);
    }
}
