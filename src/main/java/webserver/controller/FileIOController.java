package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class FileIOController extends AbstractController {
    private final String prefix;

    public FileIOController(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(String.format("./%s%s", prefix, request.getPath()));
        String accept = request.getHeader("Accept");
        response.response200Header(body.length, accept.split(",")[0]);
        response.responseBody(body);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws UnsupportedEncodingException {
        super.doPost(request, response);
    }
}
