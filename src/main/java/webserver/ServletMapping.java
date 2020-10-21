package webserver;

import java.util.HashMap;
import java.util.Map;

import webserver.servlet.PageServlet;
import webserver.servlet.Servlet;
import webserver.servlet.UserCreateServlet;

public class ServletMapping {
    private static final Map<String, Servlet> mapping;

    static {
        mapping = new HashMap<>();
        mapping.put("/user/create", new UserCreateServlet());
    }

    public static Servlet getServlet(String path) {
        if (path == null) {
            throw new NullPointerException("Request-path가 존재하지 않습니다.");
        }
        return mapping.getOrDefault(path, new PageServlet());
    }
}
