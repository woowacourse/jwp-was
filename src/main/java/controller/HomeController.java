package controller;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class HomeController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public static final String PATH = "/";

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.setStatusCode(HttpStatus.OK);
            httpResponse.forward("/index.html");
        } catch (IOException | URISyntaxException e) {
            httpResponse.setStatusCode(HttpStatus.NOT_FOUND);
            logger.error(e.getMessage());
        }
    }
}
