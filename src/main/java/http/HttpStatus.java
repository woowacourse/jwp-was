package http;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    FOUND(304, "Found"),
    NOT_FOUND(404, "Not Found");

    private final int statusCode;
    private final String reason;

    HttpStatus(int statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public static HttpStatus from(int statusCode) {
        return Arrays.stream(values())
            .filter(it -> it.statusCode == statusCode)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 상태 코드 입니다."));
    }

    public String build() {
        return statusCode + " " + reason;
    }
}
