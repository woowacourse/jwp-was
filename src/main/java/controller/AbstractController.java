package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    void doGet(final HttpRequest request, final HttpResponse response) throws IOException, URISyntaxException {
        response.makeResponse(request);
    }

    void doPost(final HttpRequest request, final HttpResponse response) throws IOException, URISyntaxException {
        response.makeResponse(request);
    }
}
