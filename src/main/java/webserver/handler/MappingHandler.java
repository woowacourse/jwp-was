package webserver.handler;

import webserver.servlet.FileServlet;
import webserver.servlet.HttpServlet;

import java.util.Map;
import java.util.Optional;

public class MappingHandler {
    public static HttpServlet getDispatcher(String absPath, Map<String, Object> servlets) {
        Optional path = Optional.ofNullable(servlets.get(absPath));
        return (HttpServlet) path.orElse(new FileServlet());
    }
}
