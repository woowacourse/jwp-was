package webserver.http.response;

public enum HttpStatusCode {
    OK("200 OK"),
    FOUND("302 Found"),
    BAD_REQUEST("400 Bad Request"),
    NOT_FOUND("404 Not Found");

    private final String value;

    HttpStatusCode(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
