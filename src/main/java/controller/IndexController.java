package controller;

import http.HttpStatusCode;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class IndexController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    public static final String PATH = "/";

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("request index page : {}", httpRequest.getUri());

        try {
            httpResponse.forward(httpRequest.getPath() + "index.html");
        } catch (IOException | URISyntaxException e) {
            httpResponse.setStatusCode(HttpStatusCode.NOT_FOUND);
            e.printStackTrace();
        }
    }
}
