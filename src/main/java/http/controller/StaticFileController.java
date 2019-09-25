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
    private static final String DIR_TEMPLATES = "./templates";
    private static final String DIR_STATIC = "./static";

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String dir = httpRequest.getPath().matches("^.+\\.(html)$") ? DIR_TEMPLATES : DIR_STATIC;

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(dir + httpRequest.getPath());
            httpResponse.setResponseBody(body, httpRequest.getPath());
        } catch (java.io.FileNotFoundException e) {
            logger.debug(e.getMessage());
            httpResponse.sendNotFound();
        }
        catch (URISyntaxException | IOException e) {
            logger.debug(e.getMessage());
        }
    }
}
