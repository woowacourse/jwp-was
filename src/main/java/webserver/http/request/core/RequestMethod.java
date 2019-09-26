package webserver.http.request.core;

import webserver.http.exception.HttpRequestMethodException;

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
        return "GET".equals(method);
    }

    public boolean isPost() {
        return "POST".equals(method);
    }

    public boolean isPut() {
        return "PUT".equals(method);
    }

    public boolean isDelete() {
        return "DELETE".equals(method);
    }
}
