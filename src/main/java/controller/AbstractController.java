package controller;

import webserver.request.HttpRequest;
import webserver.request.requestline.QueryParams;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException, URISyntaxException {
        // TODO : do something with query string params
        QueryParams queryParams = request.findQueryParams();

        response.makeResponse(request);
    }

    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException, URISyntaxException {

    }
}
