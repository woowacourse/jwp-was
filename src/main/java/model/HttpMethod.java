package model;

import java.util.function.BiFunction;

public enum HttpMethod {
    GET(HttpGetService::new),
    POST(HttpPostService::new);

    private final BiFunction<HttpHeader, HttpBody, AbstractHttpService> getService;

    HttpMethod(BiFunction<HttpHeader, HttpBody, AbstractHttpService> getService) {
        this.getService = getService;
    }

    public AbstractHttpService getService(HttpHeader header, HttpBody body) {
        return getService.apply(header, body);
    }
}
