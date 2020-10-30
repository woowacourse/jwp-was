package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

public class StaticController extends AbstractController {

    private static final String STATIC = "./static";

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException, URISyntaxException {
        final byte[] body = FileIoUtils.loadFileFromClasspath(STATIC + httpRequest.getUrl());
        httpResponse.response200(body);
    }
}
