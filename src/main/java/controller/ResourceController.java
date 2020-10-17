package controller;

import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController extends AbstractController {
    @Override
    protected void doGet(Request request, Response response) throws IOException, URISyntaxException {
        String requestUrl = request.getPath();
        String contentType = request.getContentType();

        response.ok(requestUrl, contentType);
    }
}
