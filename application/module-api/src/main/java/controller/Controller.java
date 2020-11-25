package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import domain.HttpRequest;
import domain.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;
}
