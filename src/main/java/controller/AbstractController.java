package controller;

import controller.exception.NotAllowedMethodException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController implements Controller {
    private final Map<HttpMethod, Controller> map = new HashMap<>();

    {
        map.put(HttpMethod.GET, this::doGet);
        map.put(HttpMethod.POST, this::doPost);
    }

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        HttpMethod httpMethod = httpRequest.getRequestLine().getHttpMethod();
        map.get(httpMethod).service(httpRequest, httpResponse);
    }

    void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        throw new NotAllowedMethodException();
    }

    void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        throw new NotAllowedMethodException();
    }
}
