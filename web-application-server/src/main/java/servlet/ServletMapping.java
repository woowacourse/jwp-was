package servlet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import annotation.RequestMapping;
import controller.LoginController;
import controller.UserController;
import http.request.HttpRequest;
import service.UserService;

public class ServletMapping implements HandlerMapping {
    private static final Map<String, HttpServlet> SERVLET_MATCHER;

    static {
        UserService userService = new UserService();
        UserController userController = new UserController(userService);
        LoginController loginController = new LoginController(userService);

        List<HttpServlet> servlets = Arrays.asList(userController, loginController);
        SERVLET_MATCHER = servlets.stream()
            .collect(Collectors.toMap(
                servlet -> servlet.getClass().getAnnotation(RequestMapping.class).path(),
                servlet -> servlet));
    }

    @Override
    public HttpServlet getServlet(HttpRequest httpRequest) {
        return SERVLET_MATCHER.get(httpRequest.getURI());
    }
}
