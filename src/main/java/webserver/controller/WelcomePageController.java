package webserver.controller;

import webserver.HttpRequest;
import webserver.exception.NotSupportedHttpMethodException;

public class WelcomePageController extends AbstractController {
    public static final String PATH = "/";
    private static final WelcomePageController INSTANCE = new WelcomePageController();

    public static WelcomePageController getInstance() {
        return INSTANCE;
    }

    @Override
    protected String doGet(HttpRequest httpRequest) {
        return "/index.html";
    }

    @Override
    protected String doPost(HttpRequest httpRequest) {
        throw new NotSupportedHttpMethodException();
    }
}
