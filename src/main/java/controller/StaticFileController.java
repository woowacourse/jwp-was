package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import resource.ContentType;
import resource.Resource;
import resource.ResourcesHandler;
import response.HttpResponse;
import response.StatusCode;
import webserver.RequestHandler;

public class StaticFileController {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private ResourcesHandler resourcesHandler = new ResourcesHandler();

    public HttpResponse findStaticFile(HttpRequest httpRequest) {
        Resource resourceForResponse;

        try {
            resourceForResponse = resourcesHandler.convertUriToResource(httpRequest.getUriPath());

            byte[] body = resourceForResponse.getResource();
            ContentType contentType = resourceForResponse.getContentType();

            return new HttpResponse(StatusCode.OK, body, contentType);

        } catch (IOException e) {
            logger.error("fail to read static file that uri is {} : {}",
                httpRequest.getUriPath(),
                e.getMessage()
            );
            return new HttpResponse(StatusCode.INTERNAL_SERVER_ERROR);
        } catch (URISyntaxException e) {
            logger.error("There is no corresponding file for uri {}. : {}",
                httpRequest.getUriPath(),
                e.getMessage()
            );
            return new HttpResponse(StatusCode.NOT_FOUND);
        }
    }
}
