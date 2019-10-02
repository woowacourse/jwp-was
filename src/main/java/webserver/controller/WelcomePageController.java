package webserver.controller;

import webserver.View;
import webserver.http.HttpRequest;

public class WelcomePageController extends AbstractController {
    public static final String PATH = "/";

    @Override
    protected View doGet(HttpRequest httpRequest) {
        return new View("/index.html");
    }
}
