package servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class IndexServlet extends AbstractServlet {

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        response.forward("./templates/index.html");
    }
}
