package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

public class TemplateController extends AbstractController {

    private static final String TEMPLATES = "./templates";

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException, URISyntaxException {
        final byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES + httpRequest.getUrl());
        httpResponse.response200(body);
    }
}
