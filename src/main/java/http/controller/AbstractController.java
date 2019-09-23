package http.controller;

import http.exception.MethodNotAllowedException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isGet()) {
            doGet(httpRequest, httpResponse);
        }
        if (httpRequest.isPost()) {
            doPost(httpRequest, httpResponse);
        }
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }
}
