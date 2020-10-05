package webserver.request;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE"),
    PUT("PUT"),
    PATCH("PATCH");

    private final String methodName;

    HttpMethod(String methodName) {
        this.methodName = methodName;
    }

    public static HttpMethod from(String methodName) {
        return Arrays.stream(values())
                .filter(method -> method.methodName.equals(methodName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("허용되지 않은 HttpMethod: " + methodName));
    }

    public String getMethodName() {
        return methodName;
    }
}
