package http.response.core;

import java.util.Arrays;

public enum ResponseStatus {
    OK("OK", 200),
    FOUND("Found", 302),
    FORBIDDEN("Forbidden", 403),
    NOT_FOUND("Not Found", 404),
    INTERNAL_SERVER_ERROR("Internal Server Error", 500);

    private String httpStatus;
    private int httpStatusCode;

    ResponseStatus(String httpStatus, int httpStatusCode) {
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
    }

    public static ResponseStatus of(String httpStatus) {
        return Arrays.stream(values())
                .filter(value -> value.httpStatus.equals(httpStatus))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getResponseStatusHeader() {
        return httpStatusCode + httpStatus + " \r\n";
    }
}
