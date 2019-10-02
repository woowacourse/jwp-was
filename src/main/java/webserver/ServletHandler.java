package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import servlet.Servlet;

public class ServletHandler {
    public static void handle(HttpRequest request, HttpResponse response) {
        String path = request.getUri().getPath();
        Servlet servlet = ServletMapper.getServlet(path);

        servlet.handle(request, response);
    }
}