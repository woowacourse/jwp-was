package webserver.controller;

import webserver.http.URL;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class ResourceController extends AbstractController {
    public ResourceController() {
        this.paths = Arrays.asList(".html", ".ico", ".css", ".js", "woff", "ttf");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        URL url = httpRequest.getUrl();
        httpResponse.forward(url.getPath());
    }
}
