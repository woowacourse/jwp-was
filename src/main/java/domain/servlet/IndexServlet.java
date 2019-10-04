package domain.servlet;

import was.http.servlet.AbstractServlet;
import server.http.request.HttpRequest;
import server.http.response.HttpResponse;

public class IndexServlet extends AbstractServlet {
    @Override
    protected HttpResponse doGet(final HttpRequest request) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.forward("./templates/index.html");
        return httpResponse;
    }
}
