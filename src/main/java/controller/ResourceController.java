package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.Request;
import http.response.Response;

public class ResourceController extends AbstractController {
    public void doGet(Request request, Response response) throws IOException, URISyntaxException {
        String requestUrl = request.getPath();
        String contentType = request.getContentType();

        response.ok(requestUrl, contentType);
    }
}
