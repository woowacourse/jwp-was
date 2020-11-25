package kr.wootecat.dongle.model.handler;

import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;
import kr.wootecat.dongle.model.servlet.HttpServlet;
import kr.wootecat.dongle.model.servlet.ServletMapper;

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
