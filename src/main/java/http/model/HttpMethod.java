package http.model;

import http.controller.NotFoundException;

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
                .orElseThrow(NotFoundException::new);
    }


    public boolean match(HttpMethod method) {
        return this.equals(method);
    }
}
