package utils;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE;

    HttpMethod() {
    }

    public static HttpMethod of(String method) {
        return Stream.of(values())
            .filter(methods -> methods.toString().equals(method))
            .findFirst()
            .orElseThrow(NoSuchElementException::new);
    }
}
