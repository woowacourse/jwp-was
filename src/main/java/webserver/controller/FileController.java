package webserver.controller;

import http.request.Request;
import http.response.Response;
import utils.FileIoUtils;
import webserver.support.ContentTypeHandler;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends HttpController {
    private static final int OK_STATUS_CODE = 200;
    private static final String OK_REASON_PHRASE = "OK";

    private FileController() {
    }

    public static FileController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FileController INSTANCE = new FileController();
    }

    @Override
    protected void doGet(Request request, Response response) throws IOException, URISyntaxException {
        String url = request.extractUrl();

        response.setStatusCode(OK_STATUS_CODE);
        response.setReasonPhrase(OK_REASON_PHRASE);
        response.setContentType(ContentTypeHandler.type(url.substring(url.lastIndexOf(".") + 1)));

        String absoluteUrl = PathHandler.path(url);
        byte[] body = FileIoUtils.loadFileFromClasspath(absoluteUrl);
        response.setResponseBody(body);
    }
}
