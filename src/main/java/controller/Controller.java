package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.HttpRequest;
import http.HttpResponse;

public interface Controller {
    void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException, URISyntaxException;
}
