package webserver;

import http.HttpHeaders;
import http.MediaType;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseBody;
import utils.FileIoUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import static http.HttpHeaders.ACCEPT;
import static http.MediaType.HTML;
import static http.response.HttpStatus.INTERNAL_SERVER_ERROR;
import static http.response.HttpStatus.NOT_FOUND;

public class StaticResourceHandler {
    public static final String VIEW_TEMPLATE_PATH = "./templates";
    public static final String STATIC_PATH = "./static";

    private static final Integer MOST_PREFERRED = 0;
    private static final String ERROR_PAGE = "/error.html";
    private static final String ACCEPT_HEADER_DELIMITER = ",";

    public static void forward(HttpRequest request, HttpResponse response) {
        MediaType type = mediaTypeOf(request);
        byte[] content;

        try {
            content = loadFile(request);
            response.setBody(new ResponseBody(content, type));
        } catch (FileNotFoundException e) {
            handle404NotFound(response);
        } catch (IOException | URISyntaxException e) {
            response.setStatus(INTERNAL_SERVER_ERROR);
        }
    }

    private static byte[] loadFile(HttpRequest request) throws IOException, URISyntaxException {
        String path = request.getPath();

        if (mediaTypeOf(request).equals(HTML)) {
            return FileIoUtils.loadFileFromClasspath(VIEW_TEMPLATE_PATH + path);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_PATH + path);
    }

    private static MediaType mediaTypeOf(HttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String acceptHeader = headers.getHeader(ACCEPT);

        try {
            String[] accepts = acceptHeader.split(ACCEPT_HEADER_DELIMITER);
            return MediaType.of(accepts[MOST_PREFERRED]);
        } catch (NullPointerException e) {
            return HTML;
        }
    }

    public static void handle404NotFound(HttpResponse response) {
        byte[] content;
        try {
            content = FileIoUtils.loadFileFromClasspath(VIEW_TEMPLATE_PATH + ERROR_PAGE);
            response.setBody(content);
            response.setStatus(NOT_FOUND);
        } catch (IOException | URISyntaxException e) {
            response.setStatus(INTERNAL_SERVER_ERROR);
        }
    }
}
