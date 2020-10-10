package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

public abstract class AbstractController implements Controller {

    private final Map<HttpMethod, BiConsumer<HttpRequest, HttpResponse>> methodMappings;

    public AbstractController() {
        this.methodMappings = new HashMap<>();
        methodMappings.put(HttpMethod.GET, this::doGet);
        methodMappings.put(HttpMethod.POST, this::doPost);
    }

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        methodMappings.get(httpRequest.getMethod())
                .accept(httpRequest, httpResponse);
    }

    protected void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        throw new MethodNotSupportException("지원하지 않는 메서드입니다. method: post, httpRequest: " + httpRequest);
    }

    protected void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        throw new MethodNotSupportException("지원하지 않는 메서드입니다. method: post, httpRequest: " + httpRequest);
    }
}
