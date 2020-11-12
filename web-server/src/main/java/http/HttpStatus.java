package http;

public enum HttpStatus {

    OK("200 OK"),
    CREATED("201 Created"),
    NO_CONTENT("204 No Content"),
    FOUND("302 Found"),
    NOT_MODIFIED("304 Not Modified"),
    BAD_REQUEST("400 Bad Request"),
    UNAUTHORIZED("401 Unauthorized"),
    FORBIDDEN("403 Forbidden"),
    NOT_FOUND("404 Not Found"),
    ERROR("500 Internal Server Error");

    private String message;

    HttpStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
