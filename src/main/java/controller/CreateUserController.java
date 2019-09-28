package controller;

import http.request.HttpRequest;
import http.request.HttpRequestMethod;
import http.response.HttpResponse;
import view.View;

public class CreateUserController extends AbstractController {

    @Override
    public View service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getMethod().equals(HttpRequestMethod.GET)) {
            return doGet(httpRequest, httpResponse);
        }

        return doPost(httpRequest, httpResponse);
    }

    @Override
    public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }

    @Override
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }
}
