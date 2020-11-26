package request;

import java.util.Arrays;

public enum Method {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String method;

    Method(String method) {
        this.method = method;
    }

    public static Method from(String method) {
        return Arrays.stream(values())
            .filter(methodType -> methodType.method.equals(method))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("this HTTP method does not exist."));
    }

    public String getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return method;
    }
}
