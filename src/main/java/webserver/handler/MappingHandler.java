package webserver.handler;

import webserver.servlet.FileServlet;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import java.util.HashMap;
import java.util.Map;

public class MappingHandler {
    private static Map<String, Object> servlets = new HashMap<>();

    static {
        servlets.put("/", HomeServlet.getInstance());
        servlets.put("/user/create", UserCreateServlet.getInstance());
    }

    public static HttpServlet getServlets(String absPath) {
        return (HttpServlet) servlets.getOrDefault(absPath, FileServlet.getInstance());
    }
}
