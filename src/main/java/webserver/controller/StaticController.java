package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class StaticController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(String.format("./static%s", request.getPath()));
        String accept = request.getHeader("Accept");
        response.response200Header(body.length, accept.split(",")[0]);
        response.responseBody(body);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws UnsupportedEncodingException {
        super.doPost(request, response);
    }
}
