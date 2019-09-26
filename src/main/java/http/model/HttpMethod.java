package http.model;

import http.supoort.IllegalHttpRequestException;

import java.util.Arrays;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public static HttpMethod of(String method) {
        return Arrays.asList(HttpMethod.values()).stream()
                .filter(value -> method.toUpperCase().equals(value.name()))
                .findAny()
                .orElseThrow(IllegalHttpRequestException::new);
    }
}
