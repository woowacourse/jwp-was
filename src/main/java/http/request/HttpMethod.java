package http.request;

import http.exception.HttpMethodMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    private static final Map<String, HttpMethod> mappings = new HashMap<>();

    static {
        for (HttpMethod httpMethod : values()) {
            mappings.put(httpMethod.name(), httpMethod);
        }
    }

    public static HttpMethod resolve(String method) {
        HttpMethod httpMethod = mappings.get(method);
        if (Objects.isNull(httpMethod)) {
            throw new HttpMethodMismatchException();
        }
        return httpMethod;
    }
}
