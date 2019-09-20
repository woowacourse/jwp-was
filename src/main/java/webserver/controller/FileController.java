package webserver.controller;

import http.Request;
import http.Response;
import utils.FileIoUtils;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends HttpController {
    private static final int OK_STATUS_CODE = 200;

    @Override
    protected void doGet(Request request, Response response) throws IOException, URISyntaxException {
        String url = request.extractUrl();

        response.setUrl(url);
        response.setStatusCode(OK_STATUS_CODE);
        response.setType(url.substring(url.lastIndexOf(".") + 1));

        String absoluteUrl = PathHandler.path(response.getHeaderUrl());
        byte[] body = FileIoUtils.loadFileFromClasspath(absoluteUrl);
        response.setBody(body);
    }
}
