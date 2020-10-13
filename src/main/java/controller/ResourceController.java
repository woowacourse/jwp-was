package controller;

import web.request.HttpRequest;
import web.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController extends AbstractController {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            response.ok(request.getTarget(), request.getAcceptType());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
