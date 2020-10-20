package web.response;

public enum ResponseStatus {
    OK(200, "OK"),
    FOUND(302, "FOUND"),
    BAD_REQUEST(400, "BAD REQUEST");

    private final int code;
    private final String name;

    ResponseStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return code + " " + name;
    }
}
