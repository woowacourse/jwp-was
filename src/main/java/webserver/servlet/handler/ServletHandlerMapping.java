package webserver.servlet.handler;

import webserver.servlet.HttpServlet;
import webserver.servlet.ServletMapper;
import webserver.servlet.http.request.HttpRequest;
import webserver.servlet.http.response.HttpResponse;

public class ServletHandlerMapping implements HandlerMapping {

    private final ServletMapper servletMapper;

    public ServletHandlerMapping(ServletMapper servletMapper) {
        this.servletMapper = servletMapper;
    }

    @Override
    public boolean isSupport(HttpRequest request) {
        return !request.isStaticResourceRequest();
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        HttpServlet servlet = servletMapper.get(request.getPath());
        servlet.doService(request, response);
    }
}
