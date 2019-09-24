package http;

import java.util.Arrays;

public enum HttpStatus {
    OK("200", "Ok"),
    FOUND("302", "Found");

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
