package http;

import java.util.Arrays;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE,
    NOT_SUPPORT_METHOD;

    public static HttpMethod from(String httpMethod) {
        return Arrays.stream(values())
            .filter(method -> method.name().equals(httpMethod))
            .findFirst()
            .orElse(NOT_SUPPORT_METHOD);
    }

    public HttpMethod get() {
        return this;
    }
}
