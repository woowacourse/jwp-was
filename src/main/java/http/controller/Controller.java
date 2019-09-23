package http.controller;

import http.model.request.ServletRequest;
import http.model.response.ServletResponse;

public interface Controller {
    void handle(ServletRequest servletRequest, ServletResponse servletResponse);

    boolean canHandle(ServletRequest servletRequest);
}
