package webserver.controller;

import web.request.HttpRequest;
import web.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class IndexController extends AbstractController {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            response.ok("/index.html", "*/*");
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
