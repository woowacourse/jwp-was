package http;

public enum HttpStatus {
    OK(200, "OK"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");

    private int code;
    private String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
