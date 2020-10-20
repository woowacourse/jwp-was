package model;

import java.util.function.BiFunction;

public enum HttpMethod {
    GET(HttpGetService::new),
    POST(HttpPostService::new);

    private final BiFunction<HttpHeader, HttpBody, AbstractHttpService> createService;

    HttpMethod(BiFunction<HttpHeader, HttpBody, AbstractHttpService> createService) {
        this.createService = createService;
    }

    public AbstractHttpService createService(HttpHeader header, HttpBody body) {
        return createService.apply(header, body);
    }
}
