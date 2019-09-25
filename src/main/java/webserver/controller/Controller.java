package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;
}
