package domain.servlet;

import was.http.servlet.AbstractServlet;
import was.http.request.HttpRequest;
import was.http.response.HttpResponse;

public class IndexServlet extends AbstractServlet {
    @Override
    protected HttpResponse doGet(final HttpRequest request) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.forward("./templates/index.html");
        return httpResponse;
    }
}
