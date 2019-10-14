package http.model;

public enum HttpStatus {
    OK("200 OK"),
    FOUND("302 Found"),
    BAD_REQUEST("400 Bad Request"),
    NOT_FOUND("404 Not Found"),
    SERVER_ERROR("500 Internal Server Error");

    private String message;

    HttpStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
