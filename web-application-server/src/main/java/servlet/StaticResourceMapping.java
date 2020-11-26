package servlet;

import http.request.HttpRequest;

public class StaticResourceMapping implements HandlerMapping {
    private static final ResourceServlet RESOURCE_SERVLET = new ResourceServlet();

    @Override
    public HttpServlet getServlet(HttpRequest httpRequest) {
        return RESOURCE_SERVLET;
    }
}
