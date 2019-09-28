package webserver.http.headerfields;

import webserver.http.exception.InvalidHttpMethodException;

import java.util.stream.Stream;

public enum HttpMethod {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    CONNECT("CONNECT"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    PATCH("PATCH");

    private final String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod of(String name) {
        return Stream.of(values())
                .filter(x -> x.name.equals(name.toUpperCase()))
                .findAny()
                .orElseThrow(InvalidHttpMethodException::new);
    }

    @Override
    public String toString() {
        return this.name;
    }
}