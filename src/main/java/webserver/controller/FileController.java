package webserver.controller;

import http.HttpRequest;
import http.HttpResponse;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends HttpController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String url = httpRequest.getResourcePath();
        String absoluteUrl = PathHandler.path(url);
        httpResponse.addHeader("Content-Type", "text/"+ url.substring(url.lastIndexOf(".")  + 1));
        httpResponse.forward(absoluteUrl);
    }
}
