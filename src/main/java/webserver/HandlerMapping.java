package webserver;

import application.web.UserServlet;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private final Map<String, Servlet> handlerMappings;

    public HandlerMapping() {
        handlerMappings = new HashMap<>();
        handlerMappings.put("/user/create", new UserServlet());
    }

    public Servlet findServlet(String path) {
        return handlerMappings.get(path);
    }
}
