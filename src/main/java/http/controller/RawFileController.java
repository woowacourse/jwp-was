package http.controller;

import http.HeaderParam;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.HttpResponseHeaderParser;
import webserver.RequestHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class RawFileController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private String filePath;

    public RawFileController(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        try {
            String header;
            byte[] body;
            if (httpRequest.headerContainsValueOf(HeaderParam.ACCEPT, "css")) {
                body = FileIoUtils.loadFileFromClasspath("./static" + filePath);
                header = HttpResponseHeaderParser.ok("text/css", body.length);
            } else {
                body = FileIoUtils.loadFileFromClasspath("./templates" + filePath);
                header = HttpResponseHeaderParser.ok("text/html;charset=utf-8", body.length);
            }
            return new HttpResponse(header, body);
        } catch (NullPointerException e) {
            String header = HttpResponseHeaderParser.notFound();
            return new HttpResponse(header);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            String header = HttpResponseHeaderParser.internalServerError();
            return new HttpResponse(header);
        }
    }

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        String header = HttpResponseHeaderParser.methodNotAllowed();
        return new HttpResponse(header);
    }
}
