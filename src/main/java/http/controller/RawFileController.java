package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.HttpResponseHeaderParser;
import webserver.RequestHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class RawFileController extends Controller {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final String filePath;

    public RawFileController(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        try {
            FileType fileType = FileType.of(extractExtension());
            byte[] body = FileIoUtils.loadFileFromClasspath(fileType.getRootPath() + filePath);
            String header = HttpResponseHeaderParser.ok(fileType.getContentType(), body.length);
            return new HttpResponse(header, body);
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            String header = HttpResponseHeaderParser.notFound();
            return new HttpResponse(header);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            String header = HttpResponseHeaderParser.internalServerError();
            return new HttpResponse(header);
        }
    }

    private String extractExtension() {
        String[] token = filePath.split("\\.");
        if (token.length < 2) {
            return "";
        }
        return token[token.length - 1];
    }
}
