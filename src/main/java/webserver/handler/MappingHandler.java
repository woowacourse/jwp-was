package webserver.handler;

import webserver.servlet.FileServlet;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import java.util.HashMap;
import java.util.Map;

public class MappingHandler {
    private static Map<String, HttpServlet> servlets = new HashMap<>();
    private static HttpServlet fileServlet;

    static {
        servlets.put("/", new HomeServlet());
        servlets.put("/user/create", new UserCreateServlet());
        fileServlet = new FileServlet();
    }

    public static HttpServlet getServlets(String absPath) {
        return servlets.getOrDefault(absPath, fileServlet);
    }
}
