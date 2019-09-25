package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class IndexController extends Controller {
    public static HttpResponse index(HttpRequest req) {
        return serveStaticFile("/index.html", req);
    }
}