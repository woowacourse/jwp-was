package webserver.response;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "FOUND");

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
