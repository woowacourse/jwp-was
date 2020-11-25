package webserver.response;

public enum Status {
    OK(200),
    FOUND(302);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code + " " + name();
    }
}
