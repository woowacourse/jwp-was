package webserver.handler;

import exceptions.NotFoundURIException;
import webserver.request.RequestUri;
import webserver.servlet.FileServlet;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MappingHandler {
    private static Map<String, HttpServlet> servlets = new HashMap<>();
    private static FileServlet fileServlet;

    static {
        servlets.put("/", new HomeServlet());
        servlets.put("/user/create", new UserCreateServlet());
        fileServlet = new FileServlet();
    }

    public static HttpServlet getServlets(RequestUri requestUri) {
        if (requestUri.isFile()) {
            return fileServlet;
        }
        return Optional.ofNullable(servlets.get(requestUri.getAbsPath()))
                .orElseThrow(() -> new NotFoundURIException(requestUri.getAbsPath()));
    }
}
