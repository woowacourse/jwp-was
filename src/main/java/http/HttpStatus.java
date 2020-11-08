package http;

public enum HttpStatus {
    OK("OK", 200),
    FOUND("Found", 300),
    BAD_REQUEST("Bad Request", 400),
    NOT_FOUND("Not Found", 404),
    METHOD_NOT_ALLOWED("Method Not Allowed", 405),
    INTERNAL_SERVER_ERROR("Internal Server Error", 500);

    private final String reasonPhrase;
    private final int value;

    HttpStatus(String reasonPhrase, int value) {
        this.reasonPhrase = reasonPhrase;
        this.value = value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public int getValue() {
        return value;
    }
}
