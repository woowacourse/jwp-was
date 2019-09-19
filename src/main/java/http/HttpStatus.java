package http;

import http.exception.NotFoundStatusException;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK");

    private int statusCode;
    private String status;

    HttpStatus(int statusCode, String status) {
        this.statusCode = statusCode;
        this.status = status;
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
        return status;
    }
}