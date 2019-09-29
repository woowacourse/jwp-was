package webserver.http.request;

import was.exception.MethodNotAllowedException;

import java.util.Arrays;

public enum HttpMethod {
    GET(),
    POST(),
    PUT(),
    DELETE();

    public static HttpMethod of(final String value) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.match(value))
                .findFirst()
                .orElseThrow(MethodNotAllowedException::new)
                ;
    }

    private boolean match(final String value) {
        return this.name().equals(value);
    }
}
