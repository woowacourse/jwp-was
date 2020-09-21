package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class IndexController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        httpResponse.forward(httpRequest.getPath());
    }
}
