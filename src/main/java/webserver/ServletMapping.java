package webserver;

import servlet.IndexServlet;
import servlet.Servlet;
import servlet.SignupServlet;

import java.util.HashMap;
import java.util.Map;

public class ServletMapping {
    private final Map<String, Servlet> servlets;

    public ServletMapping() {
        servlets = new HashMap<>();

        servlets.put("/", new IndexServlet());
        servlets.put("/signup", new SignupServlet());
    }

    public Servlet getServlet(String path) {
        return servlets.get(path);
    }

    public static ServletMapping getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static class LazyHolder {
        private static final ServletMapping INSTANCE = new ServletMapping();
    }
}
