package controller;

import http.HttpStatusCode;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController() {
    }

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("request file: {}", httpRequest.getUri());

        try {
            httpResponse.forward(httpRequest.getUri());
        } catch (IOException | URISyntaxException e) {
            httpResponse.setStatusCode(HttpStatusCode.NOT_FOUND);
            e.printStackTrace();
        }
    }
}

