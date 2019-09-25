package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class IndexController extends Controller {
    public static HttpResponse index(HttpRequest req) {
        return redirectTo(req, "/index.html");
    }
}