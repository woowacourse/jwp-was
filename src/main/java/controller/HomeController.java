package controller;

import utils.ControllerUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.env.RequestMapping;
import webserver.env.Singleton;
import webserver.httpelement.HttpMethod;

@Singleton
public final class HomeController implements Controller {
    @Override
    @RequestMapping(method = HttpMethod.GET, path = "/")
    public HttpResponse handle(HttpRequest req) {
        return ControllerUtils.serveStaticFile("/index.html", req);
    }
}