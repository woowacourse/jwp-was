package web.application.controller;

import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

public class PageNotFoundController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.respondPageNotFound();
    }
}
