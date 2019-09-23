package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends BasicController {

    @Override
    public HttpResponse doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        byte[] body;

        body = FileIoUtils.loadFileFromClasspath(String.format("./templates%s", request.getPath()));
        String[] splitPath = request.getPath().split("\\.");
        String contentType = splitPath[splitPath.length - 1];
        // html 찾기;
        if (body != null) {
            response.okResponse(contentType,body);
            return response;
        }

        //css,js 찾기
        body = FileIoUtils.loadFileFromClasspath(String.format("./static%s", request.getPath()));
        if (body == null) {
            return null;
        }
        response.okResponse(contentType, body);

        return response;
    }

    @Override
    public HttpResponse doPost(HttpRequest request, HttpResponse response) throws IOException {
        return null;
    }
}
