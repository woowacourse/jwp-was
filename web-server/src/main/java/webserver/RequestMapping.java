package webserver;

import java.util.HashMap;
import java.util.Map;

import web.controller.Controller;

public class RequestMapping {
    private static Map<String, Controller> controllers = new HashMap<>();

    public static Controller getController(String url) {
        return controllers.get(url);
    }

    public static void addUrl(String url, Controller controller) {
        controllers.put(url, controller);
    }
}
