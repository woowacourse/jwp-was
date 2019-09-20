package http;

import http.exception.NotFoundStatusException;

import java.util.Arrays;

public enum HttpStatus {
    OK(200),
    FOUND(302);

    private int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public static HttpStatus of(int statusCode) {
        return Arrays.stream(HttpStatus.values())
                .filter(httpStatus -> httpStatus.statusCode == statusCode)
                .findAny()
                .orElseThrow(NotFoundStatusException::new)
                ;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return this.name();
    }
}