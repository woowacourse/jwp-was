package webserver.controller;

import http.request.Request;
import http.response.Response;
import webserver.exception.InvalidRequestMethodException;

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

    protected void doGet(Request request, Response response) throws IOException, URISyntaxException {
        throw new InvalidRequestMethodException();
    }

    protected void doPost(Request request, Response response) {
        throw new InvalidRequestMethodException();
    };
}
