package http;

import java.util.Arrays;

import exceptions.InvalidHttpRequestException;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE;

    public static HttpMethod from(String method) {
        return Arrays.stream(values())
            .filter(httpMethod -> httpMethod.name().equals(method))
            .findAny()
            .orElseThrow(InvalidHttpRequestException::new);
    }
}
