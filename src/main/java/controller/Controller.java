package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {

    void run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;
}
