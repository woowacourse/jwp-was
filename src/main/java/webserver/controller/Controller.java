package webserver.controller;

import http.Request;
import http.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    void service(Request request, Response response) throws IOException, URISyntaxException;
}
