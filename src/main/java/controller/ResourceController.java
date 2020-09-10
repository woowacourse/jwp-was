package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.Request;
import http.response.Response;
import utils.FileIoUtils;

public class ResourceController implements Controller{

    @Override
    public void run(Request request, Response response) throws IOException, URISyntaxException {
        byte[] body = loadFileFromClasspath(request);
        response.response200Header(request.getHeaderByName("Accept").split(",")[0], body.length);
        response.responseBody(body);
    }

    private byte[] loadFileFromClasspath(Request request) throws IOException, URISyntaxException {
        if (request.getUri().equals("/favicon.ico")) {
            return FileIoUtils.loadFileFromClasspath("./templates" + request.getUri());
        }
        return FileIoUtils.loadFileFromClasspath("./static" + request.getUri());
    }
}
