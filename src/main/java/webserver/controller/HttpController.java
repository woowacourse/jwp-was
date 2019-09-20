package webserver.controller;

import http.Request;
import http.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class HttpController implements Controller {

    @Override
    public void service(Request request, Response response) throws IOException, URISyntaxException {
        doGet(request, response);
        doPost(request, response);
    }

    protected void doGet(Request request, Response response) throws IOException, URISyntaxException {
    }

    protected void doPost(Request request, Response response) {
    }
}
