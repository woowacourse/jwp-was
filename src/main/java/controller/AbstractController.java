package controller;

import controller.exception.NotSupportMethod;
import http.HttpRequestMethod;
import http.HttpStatusCode;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        try {
            if (request.getMethod().equals(HttpRequestMethod.GET)) {
                doGet(request, response);
            }

            if (request.getMethod().equals(HttpRequestMethod.POST)) {
                doPost(request, response);
            }
        } catch (NotSupportMethod e) {
            response.setStatusCode(HttpStatusCode.NOT_FOUND);
        }

    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        throw new NotSupportMethod("Not Support : " + httpRequest.getUri() + httpRequest.getMethod());
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        throw new NotSupportMethod("Not Support : " + httpRequest.getUri() + httpRequest.getMethod());
    }
}
