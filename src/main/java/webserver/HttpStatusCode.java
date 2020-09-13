package webserver;

public enum HttpStatusCode {

    OK(200, "OK"),
    CREATED(201, "CREATED"),
    NO_CONTENT(204, "NO CONTENT"),
    MOVE_PERMANENTLY(301, "MOVE PERMANENTLY"),
    FOUND(302, "FOUND"),
    BAD_REQUEST(400, "BAD REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT FOUND"),
    METHOD_NOT_ALLOW(405, "METHOD NOT ALLOW"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR");

    private static final String BLANK = " ";

    private final int code;
    private final String message;

    HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getCodeAndMessage() {
        return code + BLANK + message;
    }
}
