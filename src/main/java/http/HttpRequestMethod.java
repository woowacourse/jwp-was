package http;

import java.util.Arrays;

public enum HttpRequestMethod {
    GET("GET", false),
    POST("POST", true);

    private String method;
    private boolean hasBody;

    HttpRequestMethod(String method, boolean hasBody) {
        this.method = method;
        this.hasBody = hasBody;
    }

    public boolean hasBody() {
        return hasBody;
    }

    public static HttpRequestMethod of(String method) {
        return Arrays.stream(values())
                .filter(value -> value.method.equals(method))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                ;
    }
}

