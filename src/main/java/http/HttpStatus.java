package http;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented");

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
