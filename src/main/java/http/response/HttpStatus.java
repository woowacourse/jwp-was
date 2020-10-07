package http.response;

public enum HttpStatus {

    OK(200),
    FOUND(302),
    NOT_FOUND(404);

    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
