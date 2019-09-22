package http.controller;

import http.model.ServletRequest;
import http.model.ServletResponse;

public interface Handler {
    void handle(ServletRequest servletRequest, ServletResponse servletResponse);

    boolean canHandle(ServletRequest servletRequest);
}
