package web.response;

public enum Status {
    OK(200, "OK"),
    FOUND(302, "FOUND");

    private final int code;
    private final String name;

    Status(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return code + " " + name;
    }
}
