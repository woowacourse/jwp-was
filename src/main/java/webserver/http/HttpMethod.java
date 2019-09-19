package http;

import java.util.Optional;
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

    public static Optional<HttpMethod> of(String name) {
        return Stream.of(values())
                    .filter(x -> x.name.equals(name.toUpperCase()))
                    .findAny();
    }

    @Override
    public String toString() {
        return this.name;
    }
}