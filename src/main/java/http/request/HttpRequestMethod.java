package http.request;

import http.exception.NotFoundMethodException;

import java.util.Arrays;

public enum HttpRequestMethod {
    GET("GET"),
    POST("POST");

    private String method;

    HttpRequestMethod(String method) {
        this.method = method;
    }

    public static HttpRequestMethod of(String method) {
        return Arrays.stream(HttpRequestMethod.values())
                .filter(httpMethod -> httpMethod.method.equals(method))
                .findAny()
                .orElseThrow(NotFoundMethodException::new);
    }

    public String getMethod() {
        return method;
    }
}
