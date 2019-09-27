package webserver;

import servlet.DefaultServlet;
import servlet.Servlet;
import servlet.UserCreateServlet;
import servlet.LoginServlet;

import java.util.HashMap;
import java.util.Map;

public class ServletMapper {
    private static final Map<String, Servlet> SERVLETS;
    private static final Servlet DEFAULT_SERVLET;

    static {
        SERVLETS = new HashMap<>();
        DEFAULT_SERVLET = new DefaultServlet();
        SERVLETS.put("/user/create", new UserCreateServlet());
        SERVLETS.put("/user/login", new LoginServlet());
    }

    public static Servlet getServlet(String path) {
        return SERVLETS.getOrDefault(path, DEFAULT_SERVLET);
    }
}
