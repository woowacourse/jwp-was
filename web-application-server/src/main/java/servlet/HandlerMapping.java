package servlet;

import http.request.HttpRequest;

public interface HandlerMapping {
    HttpServlet getServlet(HttpRequest httpRequest);
}
