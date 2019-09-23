package webserver.controller;

import http.HttpRequest;
import http.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class HttpController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, Response response) throws IOException, URISyntaxException {
        doGet(httpRequest, response);
        doPost(httpRequest, response);
    }

    protected void doGet(HttpRequest httpRequest, Response response) throws IOException, URISyntaxException {
    }

    protected void doPost(HttpRequest httpRequest, Response response) {
    }
}
