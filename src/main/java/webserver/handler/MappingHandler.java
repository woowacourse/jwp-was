package webserver.handler;

import exceptions.NotFoundURIException;
import webserver.http.request.RequestUri;
import webserver.resolver.HtmlViewResolver;
import webserver.resolver.ViewResolver;
import webserver.servlet.FileServlet;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import java.util.HashMap;
import java.util.Map;

public class MappingHandler {
    private static Map<HttpServlet, ViewResolver> servlets = new HashMap<>();
    private static FileServlet fileServlet;

    static {
        servlets.put(new HomeServlet(), new HtmlViewResolver());
        servlets.put(new UserCreateServlet(), new HtmlViewResolver());
        fileServlet = new FileServlet();
    }

    public static HttpServlet getServlets(RequestUri requestUri) {
        if (fileServlet.canMapping(requestUri)) {
            return fileServlet;
        }
        return servlets.keySet().stream()
                .filter(s -> s.canMapping(requestUri))
                .findAny()
                .orElseThrow(() -> new NotFoundURIException(requestUri));
    }
}