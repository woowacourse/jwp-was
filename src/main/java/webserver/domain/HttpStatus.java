package webserver.domain;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_ERROR(500, "Internal Server Error");

    public static final String SPACE = " ";
    private final int code;
    private final String phrase;
    private final String message;

    HttpStatus(final int code, final String phrase) {
        this.code = code;
        this.phrase = phrase;
        this.message = this.code + SPACE + this.phrase;
    }

    public int code() {
        return code;
    }

    public String phrase() {
        return phrase;
    }

    @Override
    public String toString() {
        return message;
    }
}
