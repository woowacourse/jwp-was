package controller;

import exception.IllegalRequestException;
import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    void service(Request request, Response response) throws IOException, URISyntaxException, IllegalRequestException;
}
