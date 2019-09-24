package http.controller;

import com.google.common.collect.Maps;

import java.util.Map;

public class ControllerHandler {
    private static final Map<String, Controller> controllers = Maps.newHashMap();

    static {
        controllers.put("^.+\\.([a-z]+)$", new StaticFileController());
        controllers.put("^/user/create$", new CreateUserController());
    }

    public static Controller findByPath(String path) {
        return controllers.keySet().stream()
                .filter(path::matches)
                .map(controllers::get)
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                ;
    }
}
