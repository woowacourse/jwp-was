package http.response;

public enum HttpStatus {
    OK("OK", 200),
    CREATED("CREATED", 201),
    NO_CONTENT("NO CONTENT", 204),
    FOUND("FOUND", 302),
    BAD_REQUEST("BAD REQUEST", 400),
    NOT_FOUND("NOT FOUND", 404);

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
