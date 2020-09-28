package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

public class ResourceController implements Controller{

    @Override
    public void run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = loadFileFromClasspath(httpRequest);
        httpResponse.response200Header(httpRequest.getHeaderByName("Accept").split(",")[0], body.length);
        httpResponse.responseBody(body);
    }

    private byte[] loadFileFromClasspath(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpRequest.getUri().equals("/favicon.ico")) {
            return FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getUri());
        }
        return FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getUri());
    }
}
