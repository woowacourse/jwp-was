package model;

import java.util.Arrays;

public enum Method {

    GET("GET"),
    POST("POST");

    private final String method;

    Method(String method) {
        this.method = method;
    }

    public static Method of(String method) {
        return Arrays.stream(values())
            .filter(m -> m.method.equals(method))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Not Implemented Method"));
    }
}
