package http.request;

import http.NotSupportedHttpMethodException;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod fromMethodName(String name) {
        if (name == null) {
            throw NotSupportedHttpMethodException.of(name);
        }
        for (HttpMethod method : values()) {
            if (method.name().equals(name.toUpperCase())) {
                return method;
            }
        }
        throw NotSupportedHttpMethodException.of(name);
    }
}
