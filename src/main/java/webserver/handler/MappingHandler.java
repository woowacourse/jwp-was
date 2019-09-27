package webserver.handler;

import exceptions.NotFoundURIException;
import webserver.http.request.RequestUri;
import webserver.resolver.FileResolver;
import webserver.resolver.HandlebarsViewResolver;
import webserver.resolver.HtmlViewResolver;
import webserver.resolver.Resolver;
import webserver.servlet.*;

import java.util.HashMap;
import java.util.Map;

public class MappingHandler {
    private static Map<HttpServlet, Resolver> servlets = new HashMap<>();

    static {
        servlets.put(new HomeServlet(), new HtmlViewResolver());
        servlets.put(new UserCreateServlet(), new HtmlViewResolver());
        servlets.put(new UserListServlet(), new HandlebarsViewResolver());
        servlets.put(new UserLoginServlet(), new HtmlViewResolver());
        servlets.put(new FileServlet(), new FileResolver());
    }

    public static HttpServlet getServlets(RequestUri requestUri) {
        System.out.println(requestUri.getAbsPath());
        return servlets.keySet().stream()
                .filter(s -> s.canMapping(requestUri))
                .findAny()
                .orElseThrow(() -> new NotFoundURIException(requestUri));
    }

    public static Resolver getResolver(HttpServlet httpServlet) {
        return servlets.get(httpServlet);
    }
}