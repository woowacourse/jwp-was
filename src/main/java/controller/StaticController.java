package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

public class StaticController extends AbstractController {

    private static final String STATIC = "./static";

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(STATIC + httpRequest.getUrl());
        httpResponse.response200(body);
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {}
}
