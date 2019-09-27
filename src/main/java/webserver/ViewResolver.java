package webserver;

import http.HttpMimeType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.response.HttpStatus.INTERNAL_SERVER_ERROR;

public class ViewResolver {
    private static final Logger log = LoggerFactory.getLogger(ViewResolver.class);

    public static void resolveWithViewPath(HttpRequest request, HttpResponse response, String viewPath) {
        try {
            if (viewPath != null) {
                byte[] body = FileIoUtils.loadFileFromClasspath(viewPath);
                resolveWithBody(request, response, body);
            }
        } catch (IOException | URISyntaxException e) {
            log.debug(e.getMessage());
            response.error(INTERNAL_SERVER_ERROR);
        }
    }

    public static void resolveWithBody(HttpRequest request, HttpResponse response, byte[] body) {
        if (body != null) {
            HttpMimeType type = request.getMimeType();
            response.setBody(body, type);
        }
    }
}
