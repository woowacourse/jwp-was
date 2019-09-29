package http.servlet.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.request.support.HttpMethod.GET;

public abstract class HttpController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (GET.equals(httpRequest.getMethod())) {
            doGet(httpRequest, httpResponse);
            return;
        }
        doPost(httpRequest, httpResponse);
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    }
}
