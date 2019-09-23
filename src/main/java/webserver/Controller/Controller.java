package webserver.Controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

@FunctionalInterface
public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
