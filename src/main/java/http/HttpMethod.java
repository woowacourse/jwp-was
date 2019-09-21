package http;

import http.exception.NotFoundMethodException;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod of(String method) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.method.equals(method))
                .findAny()
                .orElseThrow(NotFoundMethodException::new);
    }
}
