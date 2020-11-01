package webserver.http.request;

import java.util.Arrays;

public enum HttpMethodType {
    GET, POST;

    public static HttpMethodType find(String requestMethodType) {
        return Arrays.stream(values())
                .filter(httpMethodType -> httpMethodType.name().equals(requestMethodType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
