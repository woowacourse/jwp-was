package http.response;

import http.exception.NotFoundStatusException;

import java.util.Arrays;

public enum HttpResponseStatus {
    OK(200, "OK"),
    FOUND(302, "FOUND");

    private int statusCode;
    private String status;

    HttpResponseStatus(int statusCode, String status) {
        this.statusCode = statusCode;
        this.status = status;
    }

    public static HttpResponseStatus of(int statusCode) {
        return Arrays.stream(HttpResponseStatus.values())
                .filter(httpResponseStatus -> httpResponseStatus.statusCode == statusCode)
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