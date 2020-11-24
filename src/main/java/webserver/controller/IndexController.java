package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class IndexController extends AbstractController {
    private static final String INDEX_PATH = "/index.html";

    public IndexController() {
        this.paths = Collections.singletonList("/");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        httpResponse.forward(INDEX_PATH);
    }
}
