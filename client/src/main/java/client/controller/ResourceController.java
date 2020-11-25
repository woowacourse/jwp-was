package client.controller;

import was.webserver.controller.AbstractController;
import was.webserver.http.URL;
import was.webserver.http.request.HttpRequest;
import was.webserver.http.response.HttpResponse;

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
