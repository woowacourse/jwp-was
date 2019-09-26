package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class WelcomePageController extends AbstractController {
    public static final String PATH = "/";
    private static final WelcomePageController INSTANCE = new WelcomePageController();

    public static WelcomePageController getInstance() {
        return INSTANCE;
    }

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse response) {
        return "/index.html";
    }
}
