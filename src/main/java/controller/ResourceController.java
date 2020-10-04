package controller;

import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController implements Controller {
    @Override
    public void service(Request request, Response response) throws IOException, URISyntaxException {
        String requestUrl = request.getPath();
        response.setHeader("Content-Type", request.getContentType() + ";charset=UTF-8");
        response.ok(requestUrl);
    }
}
