package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.httpelement.HttpMethod;
import webserver.router.RequestMapping;

public class IndexController extends BaseController {
    @RequestMapping(method = HttpMethod.GET, path = "/")
    public static HttpResponse index(HttpRequest req) {
        return serveStaticFile("/index.html", req);
    }
}