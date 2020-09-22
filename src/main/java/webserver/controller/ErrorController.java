package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class ErrorController extends AbstractController {

    private static final String ERROR_HTML_URL = "/error.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        httpResponse.notFound(ERROR_HTML_URL);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        httpResponse.notFound(ERROR_HTML_URL);
    }
}
