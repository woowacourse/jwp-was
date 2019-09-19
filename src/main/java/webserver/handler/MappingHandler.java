package webserver.handler;

import webserver.servlet.FileServlet;
import webserver.servlet.HttpServlet;
import webserver.request.HttpRequest;

import java.util.Map;
import java.util.Optional;

public class MappingHandler {
    public static HttpServlet getDispatcher(HttpRequest httpRequest, Map<String, Object> servlets) {
        Optional path = Optional.ofNullable(servlets.get(httpRequest.getAbsPath()));
        return (HttpServlet) path.orElse(new FileServlet());
    }
}
