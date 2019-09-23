package webserver;

import servlet.IndexServlet;
import servlet.SignupServlet;
import webserver.http.servlet.Servlet;

import java.util.HashMap;
import java.util.Map;

public class ServletMapping {
    private final Map<String, Servlet> servlets;

    private ServletMapping() {
        servlets = new HashMap<>();

        servlets.put("/", new IndexServlet());
        servlets.put("/user/create", new SignupServlet());
    }

    Servlet getServlet(String path) {
        return servlets.get(path);
    }

    boolean isMapping(String path) {
        return servlets.containsKey(path);
    }

    static ServletMapping getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final ServletMapping INSTANCE = new ServletMapping();
    }
}
