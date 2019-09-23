package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller {

    void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        response.makeResponse();
    }

    void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
        response.makeResponse();
    }
}
