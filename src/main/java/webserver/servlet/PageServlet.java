package webserver.servlet;

import http.request.HttpRequest;
import http.response.ContentType;
import http.response.HttpResponse;

public class PageServlet implements Servlet {
    @Override
    public void doService(HttpRequest request, HttpResponse response) {
        response.responseResource(request, Servlet.TEMPLATE_PATH,
                ContentType.HTML.getContentType());
    }
}
