package domain.controller;

import domain.request.HttpRequest;
import domain.response.HttpResponse;

@FunctionalInterface
public interface Controller {

    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
