package servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractServlet;

public class IndexServlet extends AbstractServlet {

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        response.forward("./templates/index.html");
    }
}
