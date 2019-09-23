package http.request.core;

import http.exception.HttpRequestMethodException;

import java.util.Arrays;

public enum RequestMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String method;

    RequestMethod(final String method) {
        this.method = method;
    }

    public static RequestMethod of(String requestMethod) {
        return Arrays.stream(RequestMethod.values())
                .filter(value -> value.method.equals(requestMethod))
                .findAny()
                .orElseThrow(HttpRequestMethodException::new);
    }

    public String getMethod() {
        return method;
    }

    public boolean isGet() {
        return method.equals("GET");
    }

    public boolean isPost() {
        return method.equals("POST");
    }
}
