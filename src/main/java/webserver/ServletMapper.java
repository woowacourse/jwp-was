package webserver;

import servlet.DefaultServlet;
import servlet.LoginServlet;
import servlet.Servlet;
import servlet.UserServlet;

import java.util.HashMap;
import java.util.Map;

public class ServletMapper {
    private static final Map<String, Servlet> SERVLETS;
    private static final Servlet DEFAULT_SERVLET;

    static {
        SERVLETS = new HashMap<>();
        DEFAULT_SERVLET = new DefaultServlet();
        SERVLETS.put("/user/create", new UserServlet());
        SERVLETS.put("/user/login", new LoginServlet());
    }

    public static Servlet getServlet(String path) {
        return SERVLETS.getOrDefault(path, DEFAULT_SERVLET);
    }
}
