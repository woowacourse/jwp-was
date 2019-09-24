package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class StaticController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(StaticController.class);
    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        log.debug(String.format("path: %s", request.getPath()));

        // prefix 만 변하는데.. 이 부분만 따로 떼어낼 수 있는감??..
        byte[] body = FileIoUtils.loadFileFromClasspath(String.format("./static%s", request.getPath()));
        String accept = request.getHeader("Accept");
        response.response200Header(body.length, accept.split(",")[0]);
        response.responseBody(body);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws UnsupportedEncodingException {
        super.doPost(request, response);
    }
}
