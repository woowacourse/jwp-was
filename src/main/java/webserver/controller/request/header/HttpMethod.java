package webserver.controller.request.header;

import exception.HttpMethodNotFoundException;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod match(String method) {
        return Arrays.stream(HttpMethod.values())
            .filter(h -> h.getMethod().equals(method))
            .findFirst()
            .orElseThrow(HttpMethodNotFoundException::new);
    }

    public String getMethod() {
        return method;
    }
}
