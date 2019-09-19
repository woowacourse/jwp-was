package servlet;

import http.AbstractServlet;
import http.HttpRequest;
import http.HttpResponse;

public class IndexServlet extends AbstractServlet {
    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        response.forward("./templates/index.html");
    }
}
