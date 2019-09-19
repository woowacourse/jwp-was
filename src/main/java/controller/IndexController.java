package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public class IndexController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException, URISyntaxException {
        doGet(request, response);
    }

    @Override
    public HttpStatus findStatus() {
        return HttpStatus.Ok;
    }
}
