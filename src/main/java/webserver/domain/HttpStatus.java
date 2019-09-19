package webserver.domain;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private final int code;
    private final String phrase;

    HttpStatus(final int code, final String phrase) {
        this.code = code;
        this.phrase = phrase;
    }

    public int code() {
        return code;
    }

    public String phrase() {
        return phrase;
    }

    @Override
    public String toString() {
        return this.code + " " + this.phrase;
    }
}
