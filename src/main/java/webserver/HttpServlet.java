package webserver;

import webserver.http.request.HttpRequest;

public abstract class HttpServlet extends GenericServlet {
    @Override
    public void doService(HttpRequest httpRequest) {
        if (httpRequest.isGetMethod()) {
            this.doGet(httpRequest);
            return;
        }
        this.doPost(httpRequest);
    }

    protected void doGet(HttpRequest httpRequest) {
        throw new UnsupportedOperationException();
    }

    protected void doPost(HttpRequest httpRequest) {
        throw new UnsupportedOperationException();
    }
}
