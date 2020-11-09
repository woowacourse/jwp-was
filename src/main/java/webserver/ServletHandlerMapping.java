package webserver;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

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
        final HttpServlet servlet = servletMapper.get(request.getPath());
        servlet.doService(request, response);
    }
}
