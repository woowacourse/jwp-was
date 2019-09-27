package webserver;

import servlet.*;

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
        SERVLETS.put("/user/list", new UserListServlet());
    }

    public static Servlet getServlet(String path) {
        return SERVLETS.getOrDefault(path, DEFAULT_SERVLET);
    }
}
