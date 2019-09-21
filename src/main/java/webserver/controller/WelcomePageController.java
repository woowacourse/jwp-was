package webserver.controller;

import webserver.Controller;
import webserver.HttpRequest;

public class WelcomePageController implements Controller {
    public static final String PATH = "/";
    private static final WelcomePageController INSTANCE = new WelcomePageController();

    public static WelcomePageController getInstance() {
        return INSTANCE;
    }

    @Override
    public String service(HttpRequest request) {
        if (request.getMethod().equals("GET")) {
            return doGet();
        }

        throw new IllegalArgumentException("지원 하지 않는 Http Method 타입입니다.");
    }

    private String doGet() {
        return "/index.html";
    }
}
