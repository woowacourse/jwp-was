package web;

import java.util.Arrays;

public enum HttpMethod {
    POST, GET, PUT, PATCH, DELETE, NONE;

    public static HttpMethod from(String value) {
        return Arrays.stream(values())
            .filter(v -> v.name().equals(value))
            .findFirst()
            .orElse(NONE);
    }
}
