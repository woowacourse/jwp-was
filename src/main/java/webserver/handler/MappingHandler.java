package webserver.handler;

import exceptions.NotFoundURIException;
import webserver.http.request.RequestUri;
import webserver.resolver.FileResolver;
import webserver.resolver.HandlebarViewResolver;
import webserver.resolver.HtmlViewResolver;
import webserver.resolver.Resolver;
import webserver.servlet.*;

import java.util.ArrayList;
import java.util.List;

public class MappingHandler {
    private static List<HttpServlet> servlets = new ArrayList<>();

    static {
        servlets.add(new HomeServlet(new HtmlViewResolver()));
        servlets.add(new UserCreateServlet(new HtmlViewResolver()));
        servlets.add(new UserListServlet(new HandlebarViewResolver()));
        servlets.add(new UserLoginServlet(new HtmlViewResolver()));
        servlets.add(new FileServlet(new FileResolver()));
    }

    public static HttpServlet getServlets(RequestUri requestUri) {
        System.out.println(requestUri.getAbsPath());
        return servlets.stream()
                .filter(s -> s.canMapping(requestUri))
                .findAny()
                .orElseThrow(() -> new NotFoundURIException(requestUri));
    }
}