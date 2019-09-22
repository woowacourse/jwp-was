package webserver.handler;

import webserver.servlet.FileServlet;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MappingHandler {
    private static Map<String, HttpServlet> servlets = new HashMap<>();
    private static FileServlet fileServlet;

    static {
        servlets.put("/", new HomeServlet());
        servlets.put("/user/create", new UserCreateServlet());
        FileServlet fileServlet = new FileServlet();
    }

    public static HttpServlet getServlets(String absPath) {
        return servlets.getOrDefault(absPath, fileServlet);
    }
}
