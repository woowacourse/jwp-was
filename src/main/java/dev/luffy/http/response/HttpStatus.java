package dev.luffy.http.response;

import java.util.Arrays;

import dev.luffy.http.response.exception.NotSupportedHttpResponseStatus;

public enum HttpStatus {
    OK(200),
    FOUND(302),
    NOT_FOUND(404);

    private int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public static HttpStatus of(int statusCode) {
        return Arrays.stream(values())
                .filter(httpStatus -> httpStatus.statusCode == statusCode)
                .findFirst()
                .orElseThrow(() -> new NotSupportedHttpResponseStatus("지원하지 않는 응답 코드입니다."));
    }

    public String getResponseLine() {
        return String.format("%d %s", statusCode, name().replace("_", " "));
    }
}
