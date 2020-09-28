package webserver.controller;

import utils.FileIoUtils;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseHeader;
import webserver.http.response.HttpResponseLine;
import webserver.http.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticFileController {
    private static final byte[] ERROR_BODY = "Error".getBytes();

    public static HttpResponse staticProcess(String filePath, HttpStatus httpStatus) {
        byte[] body = findFile(filePath);

        HttpResponseHeader httpResponseHeader = new HttpResponseHeader(new HttpResponseLine(httpStatus));
        httpResponseHeader.add("Content-Type", "text/html;charset=utf-8");
        httpResponseHeader.add("Content-Length", String.valueOf(body.length));

        return new HttpResponse(httpResponseHeader, body);
    }

    public static HttpResponse staticProcess(String filePath) {
        return staticProcess(filePath, HttpStatus.OK);
    }

    public static byte[] findFile(String filePath) {
        try {
            return FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException | URISyntaxException e) {
            return ERROR_BODY;
        }
    }
}
