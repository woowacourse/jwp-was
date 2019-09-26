package servlet.controller;

import http.HttpResponse;
import http.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class HttpController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if ("GET".equals(httpRequest.getMethod())) {
            doGet(httpRequest, httpResponse);
        }
        doPost(httpRequest, httpResponse);
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }
}
