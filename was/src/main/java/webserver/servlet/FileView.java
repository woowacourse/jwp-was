package webserver.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestURI;
import webserver.http.response.HttpResponse;

public class FileView extends AbstractView {
    private static final Logger logger = LoggerFactory.getLogger(FileView.class);

    private final RequestURI uri;

    public FileView(RequestURI uri) {
        this.uri = uri;
    }

    @Override
    void render(HttpRequest request, HttpResponse response) {
        try {
            response.ok(uri.getClassPath(), uri.getContentType());
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
            response.badRequest();
        }
    }
}
