package http;

import java.util.Arrays;

public enum HttpStatus {
    OK("200", "OK"),
    FOUND("302", "FOUND"),
    NOT_FOUND("404", "NOT FOUND"),
    METHOD_NOT_ALLOW("405", "Method Not Allowed");

    private String statusCode;
    private String reasonPhrase;

    HttpStatus(String statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public static HttpStatus of(String code) {
        return Arrays.stream(values())
                .filter(value -> value.statusCode.equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return statusCode + " " + reasonPhrase;
    }
}
