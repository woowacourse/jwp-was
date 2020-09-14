package webserver;

import exception.HttpMethodNotFoundException;
import utils.StringUtils;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod from(String methodName) {
        StringUtils.validateNonNullAndNotEmpty(methodName);

        return Arrays.stream(HttpMethod.values())
                .filter(type -> type.name.equals(methodName))
                .findFirst()
                .orElseThrow(() -> new HttpMethodNotFoundException(methodName));
    }
}

