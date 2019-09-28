package web.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class IndexController {
    public static void goIndex(HttpRequest request, HttpResponse response) {
        response.setContentType("text/html");
        response.forward("/index.html");
    }
}
