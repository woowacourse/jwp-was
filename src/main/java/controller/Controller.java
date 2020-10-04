package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.Request;
import http.response.Response;

public interface Controller {
    void service(Request request, Response response) throws IOException, URISyntaxException, NoSuchMethodException;
}
