package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticFileController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(StaticFileController.class);

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String dir = "./static";
        if (httpRequest.getPath().matches("^.+\\.(html)$")) {
            dir = "./templates";
        }

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(dir + httpRequest.getPath());
            httpResponse.ok(body);
        } catch (URISyntaxException | IOException e) {
            logger.debug(e.getMessage());
            httpResponse.sendNotFound();
        }
    }
}
