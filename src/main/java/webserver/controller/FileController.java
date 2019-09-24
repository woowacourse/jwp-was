package webserver.controller;

import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends HttpController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String url = httpRequest.getResourcePath();

        httpResponse.setUrl(url);
        httpResponse.setStatusCode(OK_STATUS_CODE);
        httpResponse.setType(url.substring(url.lastIndexOf(".") + 1));

        String absoluteUrl = PathHandler.path(httpResponse.getHeaderUrl());
        byte[] body = FileIoUtils.loadFileFromClasspath(absoluteUrl);
        httpResponse.setBody(body);
    }
}
