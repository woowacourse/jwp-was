package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller {

    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        response.makeResponse();
    }

    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
        response.makeResponse();
    }
}
