package webserver.response;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "FOUND"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOW(405, "Method Not Allow"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String name;

    HttpStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
