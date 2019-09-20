package http.request;

import exception.NoMatchHttpMethodException;

import java.util.Arrays;

public enum RequestMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String method;

    RequestMethod(String method) {
        this.method = method;
    }

    public static RequestMethod from(String requestMethod) {
        return Arrays.stream(values())
                .filter(method -> method.getMethod().equals(requestMethod))
                .findAny()
                .orElseThrow(NoMatchHttpMethodException::new);
    }

    public String getMethod() {
        return method;
    }
}
