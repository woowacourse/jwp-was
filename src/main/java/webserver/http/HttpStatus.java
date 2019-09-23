package webserver.http;

import java.util.Arrays;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    FOUND(302, "Found"),
    NOT_MODIFIED(304, "Not Modified"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String phrase;

    HttpStatus(final int code, final String phrase) {
        this.code = code;
        this.phrase = phrase;
    }

    public static HttpStatus from(final String status) {
        return Arrays.stream(values())
                .filter(httpStatus -> httpStatus.contains(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not support HttpStatus"));
    }

    private boolean contains(final String status) {
        return getFullStatus().contains(status);
    }

    public int getCode() {
        return code;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getFullStatus() {
        return String.format("%s %s", code, phrase);
    }
}
