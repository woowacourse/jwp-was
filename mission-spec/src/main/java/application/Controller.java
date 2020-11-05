package application;

import servlet.HttpRequest;
import servlet.HttpResponse;

@FunctionalInterface
public interface Controller {

    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
