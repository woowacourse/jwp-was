package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class UserController extends AbstractController {

    @Override
    protected String doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "/user/form.html";
    }
}
