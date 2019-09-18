package http.model;

public enum HttpStatus {
    OK("200 OK"),
    FOUND("302 Found"),
    BAD_REQUEST("400 Bad Request"),
    ERROR("500 Internal Server Error");

    private String message;

    private HttpStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
