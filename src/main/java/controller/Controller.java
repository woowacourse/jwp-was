package controller;

import http.HttpMethod;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Controller {
    private final Map<HttpMethod, BiConsumer<HttpRequest, HttpResponse>> mapper = new HashMap<>();

    {
        mapper.put(HttpMethod.GET, this::doGet);
        mapper.put(HttpMethod.POST, this::doPost);
    }

    public void service(HttpRequest request, HttpResponse response) {
        mapper.get(request.getMethod()).accept(request, response);
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.METHOD_NOT_ALLOW);
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.METHOD_NOT_ALLOW);
    }
}
