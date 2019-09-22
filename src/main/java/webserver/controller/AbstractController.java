package webserver.controller;

import exception.ContentTypeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.ContentType;
import webserver.controller.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    public static void movePage(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            ContentType contentType  = httpRequest.getContentType();
            byte[] body = httpRequest.getResponseBody(contentType);
            httpResponse.response200Header(body.length, contentType);
            httpResponse.responseBody(body);
        } catch (Exception e) {
            httpResponse.badRequest(e.getMessage());
        }
    }

}
