package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.FileNotFoundException;

@FunctionalInterface
public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException;
}
