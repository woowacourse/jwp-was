package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendMethodNotAllowed();

        if (httpRequest.isGet()) {
            doGet(httpRequest, httpResponse);
        }
        if (httpRequest.isPost()) {
            doPost(httpRequest, httpResponse);
        }
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
