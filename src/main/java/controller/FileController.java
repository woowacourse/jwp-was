package controller;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends Controller {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private FileController() {

    }

    public static FileController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FileController INSTANCE = new FileController();
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String uri = httpRequest.getUri();

        try {
            httpResponse.setStatusCode(HttpStatus.OK);
            httpResponse.forward(uri);
        } catch (IOException | URISyntaxException e) {
            httpResponse.setStatusCode(HttpStatus.NOT_FOUND);
            logger.error(e.getMessage());
        }

    }
}
