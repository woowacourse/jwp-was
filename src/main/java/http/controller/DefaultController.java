package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class DefaultController extends AbstractController {
    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendNotFound();
    }
}
