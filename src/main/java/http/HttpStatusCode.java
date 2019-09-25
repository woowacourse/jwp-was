package http;


import java.util.Arrays;

public enum HttpStatusCode {
    OK("200", "OK"),
    FOUND("302", "Found"),
    NOT_FOUND("404", "Not Found");

    private String statusCode;
    private String reasonPhrase;

    HttpStatusCode(String statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public static HttpStatusCode of(String code) {
        return Arrays.stream(values())
                .filter(value -> value.statusCode.equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                ;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public String toString() {
        return statusCode + " " + reasonPhrase;
    }
}
