package http.request;

import controller.exception.MethodNotAllowedException;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String method;

    HttpMethod(final String method) {
        this.method = method;
    }

    public static HttpMethod of(final String value) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.match(value))
                .findFirst()
                .orElseThrow(MethodNotAllowedException::new)
                ;
    }

    private boolean match(final String value) {
        return this.method.equals(value);
    }
}
