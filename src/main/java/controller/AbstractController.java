package controller;

import http.HttpRequestMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (request.getMethod().equals(HttpRequestMethod.GET)) {
            doGet(request, response);
        }

        if (request.getMethod().equals(HttpRequestMethod.POST)) {
            doPost(request, response);
        }
    }

    abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);

    abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;
}
