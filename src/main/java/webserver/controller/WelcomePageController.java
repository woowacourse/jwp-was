package webserver.controller;

import webserver.View;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class WelcomePageController extends AbstractController {
    public static final String PATH = "/";
    private static final WelcomePageController INSTANCE = new WelcomePageController();

    public static WelcomePageController getInstance() {
        return INSTANCE;
    }

    @Override
    protected View doGet(HttpRequest httpRequest, HttpResponse response) {
        return new View("/index.html");
    }
}
