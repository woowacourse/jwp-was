package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.function.BiConsumer;

public interface Responsive extends BiConsumer<HttpRequest, HttpResponse> {
}
