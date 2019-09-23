package webserver;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");


    private final int statusCode;
    private final String message;

    HttpStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
