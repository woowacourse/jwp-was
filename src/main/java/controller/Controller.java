package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {

    void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;

    HttpStatus findStatus();
}
