package webserver.controller;

import utils.URIUtils;
import web.request.HttpRequest;
import web.request.RequestPath;
import web.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController extends AbstractController {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            RequestPath requestPath = request.getRequestPath();
            String requestTarget = requestPath.getTarget();
            String filePath = URIUtils.getFilePath(requestTarget);

            response.ok(filePath, request.getAcceptType());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
