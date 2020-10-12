package model.general;

import java.util.Arrays;

public enum Method {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String method;

    Method(String method) {
        this.method = method;
    }

    public static Method of(String method) {
        return Arrays.stream(values())
            .filter(m -> m.method.equals(method))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Not Supported Method"));
    }
}
