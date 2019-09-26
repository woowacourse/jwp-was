package webserver.http.response.core;

import java.util.Arrays;

public enum ResponseStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private int httpStatusCode;
    private String httpStatus;

    ResponseStatus(int httpStatusCode, String httpStatus) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
    }

    public static ResponseStatus of(int httpStatusCode) {
        return Arrays.stream(values())
                .filter(value -> value.httpStatusCode == httpStatusCode)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getHttpStatus() {
        return httpStatus;
    }
}
