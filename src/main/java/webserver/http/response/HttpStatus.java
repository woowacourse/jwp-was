package webserver.http.response;

import webserver.http.response.exception.InvalidCodeException;

import java.util.Arrays;

public enum HttpStatus {
    OK(200),
    FOUND(302);

    private final int code;

    HttpStatus(final int code) {
        this.code = code;
    }

    public static HttpStatus of(final int code) {
        return Arrays.stream(HttpStatus.values())
                .filter(value -> value.match(code))
                .findFirst()
                .orElseThrow(InvalidCodeException::new);
    }

    private boolean match(final int code) {
        return this.code == code;
    }

    @Override
    public String toString() {
        return code + " " + this.name();
    }
}
