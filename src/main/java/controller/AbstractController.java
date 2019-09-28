package controller;

import http.request.HttpRequest;
import http.request.HttpRequestMethod;
import http.response.HttpResponse;
import view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class AbstractController implements Controller {

    private Map<HttpRequestMethod, BiFunction<HttpRequest, HttpResponse, View>> methodMapping = new HashMap<>();

    @Override
    public View service(HttpRequest httpRequest, HttpResponse httpResponse) {
        methodMapping.put(HttpRequestMethod.GET, this::doGet);
        methodMapping.put(HttpRequestMethod.POST, this::doPost);

        return methodMapping.get(httpRequest.getMethod()).apply(httpRequest, httpResponse);
    }

    public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        return null;
    }
}
