package servlet;

import http.HttpRequest;

public interface HandlerMapping {
    HttpServlet getServlet(HttpRequest httpRequest);
}
