package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import servlet.Servlet;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServletHandler {
    public static void handle(HttpRequest request, HttpResponse response)
            throws URISyntaxException, IOException {
        String path = request.getUri().getPath();
        Servlet servlet = ServletMapper.getServlet(path);

        servlet.handle(request, response);
    }
}