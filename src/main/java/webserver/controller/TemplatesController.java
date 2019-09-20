package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplatesController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(TemplatesController.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        byte[] body = new byte[]{};

        body = FileIoUtils.loadFileFromClasspath(String.format("./templates%s", request.getPath()));
        response.response200Header(body.length, "text/html");
        response.responseBody(body);
    }
}
