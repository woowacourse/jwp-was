package webserver.controller;

import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class HttpController implements Controller {

    @Override
    public void service(Request request, Response response) throws IOException, URISyntaxException {
        if (request.isGetMethod()) {
            doGet(request, response);
            return;
        }

        if (request.isPostMethod()) {
            doPost(request, response);
        }
    }

    protected abstract void doGet(Request request, Response response) throws IOException, URISyntaxException;

    protected abstract void doPost(Request request, Response response);
}
