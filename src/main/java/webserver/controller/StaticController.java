package webserver.controller;

import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;

public class StaticController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getPath());
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
