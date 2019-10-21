package webserver;

import http.HttpHeaders;
import http.MediaType;
import http.exception.MediaTypeNotDefinedException;
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
    private static final String ERROR_PAGE = "/error-404.html";
    private static final String ACCEPT_HEADER_DELIMITER = ",";

    public static void forward(HttpRequest request, HttpResponse response) {
        MediaType type = mediaTypeOf(request);
        byte[] content;

        try {
            content = loadFile(request);
            response.setBody(new ResponseBody(content, type));
        } catch (FileNotFoundException e) {
            response.setStatus(NOT_FOUND);
            forwardErrorPage(request, response);
        } catch (IOException | URISyntaxException e) {
            response.setStatus(INTERNAL_SERVER_ERROR);
        }
    }

    private static byte[] loadFile(HttpRequest request) throws IOException, URISyntaxException {
        String path = request.getPath();
        String directory = HTML.equals(mediaTypeOf(request))
                ? VIEW_TEMPLATE_PATH : STATIC_PATH;

        return FileIoUtils.loadFileFromClasspath(directory + path);
    }

    private static MediaType mediaTypeOf(HttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String acceptHeader = headers.getHeader(ACCEPT);

        try {
            String[] accepts = acceptHeader.split(ACCEPT_HEADER_DELIMITER);
            return MediaType.of(accepts[MOST_PREFERRED]);
        } catch (NullPointerException | MediaTypeNotDefinedException e) {
            return HTML;
        }
    }

    public static void forwardErrorPage(HttpRequest request, HttpResponse response) {
        MediaType type = mediaTypeOf(request);
        byte[] content;

        try {
            content = FileIoUtils.loadFileFromClasspath(VIEW_TEMPLATE_PATH + ERROR_PAGE);
            response.setBody(new ResponseBody(content, type));
        } catch (IOException | URISyntaxException e) {
            response.setStatus(INTERNAL_SERVER_ERROR);
        }
    }
}
