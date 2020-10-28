package controller;

import application.AbstractController;
import servlet.HttpRequest;
import servlet.HttpResponse;

public class PageNotFoundController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.respondPageNotFound();
    }
}
