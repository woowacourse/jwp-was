package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.RedirectView;
import view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class AbstractController implements Controller {
    private Map<HttpMethod, BiFunction<HttpRequest, HttpResponse, View>> methodMapping = new HashMap<>();

    @Override
    public View service(HttpRequest httpRequest, HttpResponse httpResponse) {
        methodMapping.put(HttpMethod.GET, this::doGet);
        methodMapping.put(HttpMethod.POST, this::doPost);

        return methodMapping.get(httpRequest.getMethod()).apply(httpRequest, httpResponse);
    }

    public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new RedirectView("index.html");
    }
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new RedirectView("index.html");
    }
}
