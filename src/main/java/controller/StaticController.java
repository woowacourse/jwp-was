package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.ContentType;
import http.request.Request;
import http.response.Response;

public class StaticController implements Controller {
    @Override
    public void service(Request request, Response response) throws IOException, URISyntaxException {
        String requestUrl = request.getRequestLine().getUrl();

        ContentType contentType = ContentType.of(requestUrl);
        response.setHeader("Content-Type", contentType.getContentType() + ";charset=utf-8");
        response.ok(requestUrl);
    }
}
