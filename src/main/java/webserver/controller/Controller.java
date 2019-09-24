package webserver.controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    int REDIRECT_STATUS_CODE = 302;
    int OK_STATUS_CODE = 200;

    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;
}
