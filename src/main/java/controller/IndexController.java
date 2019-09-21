package controller;

import http.HttpRequest;
import http.common.HttpHeader;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.Response200;
import http.response.ResponseBody;
import http.response.ResponseBodyParser;
import http.response.StatusLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IndexController {

    public HttpResponse doGet(HttpRequest httpRequest) throws IOException, URISyntaxException {
        StatusLine statusLine = new StatusLine(httpRequest.getHttpVersion(), HttpStatus.OK);
        ResponseBody responseBody = ResponseBodyParser.parse(httpRequest.getPath());
        HttpHeader responseHeader = new HttpHeader();
        responseHeader.putHeader("Content-Type", Files.probeContentType(Paths.get(httpRequest.getPath())));
        responseHeader.putHeader("Content-Length",Integer.toString(responseBody.getLength()));
        return new Response200(statusLine, responseHeader, responseBody);
    }
}
