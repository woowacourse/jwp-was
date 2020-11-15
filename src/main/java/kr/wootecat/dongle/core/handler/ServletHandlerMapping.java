package kr.wootecat.dongle.core.handler;

import kr.wootecat.dongle.core.servlet.HttpServlet;
import kr.wootecat.dongle.core.servlet.ServletMapper;
import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.response.HttpResponse;

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
