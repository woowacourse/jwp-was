package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class FileController extends BasicController {
    @Override
    public HttpResponse doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        byte[] body;

        body = FileIoUtils.loadFileFromClasspath(String.format("./templates%s", request.getPath()));
        String[] splitPath = request.getPath().split("\\.");
        String contentType = splitPath[splitPath.length - 1];
        List<String> headers;

        if (body != null) {
            headers = Arrays.asList("Content-Type: text/" + contentType + ";charset=utf-8\r\n",
                    "Content-Length: " + body.length + "\r\n");
            response.addHeader(headers);
            response.responseStartLine("HTTP/1.1 200 OK\r\n");
            response.responseHeader();
            response.responseBody(body);
            return response;
        }

        body = FileIoUtils.loadFileFromClasspath(String.format("./static%s", request.getPath()));
        if (body == null) {
            return null;
        }
        headers = Arrays.asList("Content-Type: text/" + contentType + ";charset=utf-8\r\n");
        response.addHeader(headers);
        response.responseStartLine("HTTP/1.1 200 OK\r\n");
        response.responseHeader();
        response.responseBody(body);
        return response;
    }

    @Override
    public HttpResponse doPost(HttpRequest request, HttpResponse response) throws IOException {
        return null;
    }
}
