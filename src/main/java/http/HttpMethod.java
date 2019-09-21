package http;

import http.exception.NotFoundMethodException;

import java.util.Arrays;

public enum HttpMethod {
    GET,
    POST;

    public static HttpMethod of(String method) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(method))
                .findAny()
                .orElseThrow(NotFoundMethodException::new);
    }
}