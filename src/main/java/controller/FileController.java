package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String uri = httpRequest.getUri();
        try {
            httpResponse.send200Ok(uri);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }

    }
}
