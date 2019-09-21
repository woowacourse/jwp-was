package servlet;

import http.AbstractServlet;
import http.HttpRequest;
import http.HttpResponse;

public class IndexServlet extends AbstractServlet {
    @Override
    protected HttpResponse doGet(final HttpRequest request) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.forward("./templates/index.html");
        return httpResponse;
    }
}
