package http.response;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Sever Error"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    private Integer code;
    private String message;

    HttpStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return code + " " + message;
    }

    public boolean match(HttpStatus httpStatus) {
        return this == httpStatus;
    }
}
