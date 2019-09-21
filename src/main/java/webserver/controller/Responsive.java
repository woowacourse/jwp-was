package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.function.Function;

public interface Responsive extends Function<HttpRequest, HttpResponse> {
    @Override
    HttpResponse apply(HttpRequest httpRequest);
}
