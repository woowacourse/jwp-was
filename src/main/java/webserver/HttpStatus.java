package webserver;

public enum HttpStatus {
    OK("OK", 200),
    FOUND("Found", 302),
    BAD_REQUEST("Bad Request", 400),
    UNAUTHORIZED("Unauthorized", 401),
    NOT_FOUND("Not Found", 404),
    METHOD_NOT_ALLOWED("Method Not Allowed", 405),
    UNPROCESSABLE_ENTITY("Unprocessable Entity", 422),
    INTERNAL_SERVER_ERROR("Internal Server Error", 500);

    private final String name;
    private final int number;

    HttpStatus(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
