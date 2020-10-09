package http;

public enum ResponseStatusLine {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private static final String VERSION = "HTTP/1.1";

    private final int code;
    private final String text;

    ResponseStatusLine(final int code, final String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public String toMessage() {
        return VERSION + " " + code + " " + text;
    }
}
