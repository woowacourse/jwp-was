package webserver.response;

public enum ResponseStatus {
    OK(200),
    FOUND(302);

    private int code;

    ResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
