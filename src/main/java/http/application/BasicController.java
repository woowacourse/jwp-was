package http.application;

import http.request.HttpRequest;
import http.request.Url;
import http.response.HttpResponse;

public class BasicController extends AbstractController {
    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        Url requestUrl = httpRequest.getRequestLine().getUrl();
        httpResponse.forward(requestUrl.getFullUrl());
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
