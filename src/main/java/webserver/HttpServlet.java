package webserver;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class HttpServlet extends GenericServlet {
    @Override
    public void doService(HttpRequest request, HttpResponse response) {
        if (request.isGetMethod()) {
            this.doGet(request, response);
            return;
        }
        this.doPost(request, response);
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        throw new UnsupportedOperationException();
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        throw new UnsupportedOperationException();
    }
}
