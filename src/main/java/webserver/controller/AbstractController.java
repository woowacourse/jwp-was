package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;
import webserver.exception.MethodNotAllowedException;
import webserver.exception.NotImplementedException;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        switch (request.method()) {
            case GET:
                doGet(request, response);
                return;
            case POST:
                doPost(request, response);
                return;
        }
        throw new NotImplementedException();
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        throw new MethodNotAllowedException();
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        throw new MethodNotAllowedException();
    }
}
