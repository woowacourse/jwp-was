package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class FailToSignInController extends AbstractController {
    public String doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "/user/login_failed.html";
    }
}
