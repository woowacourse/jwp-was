package http.common;

public enum HttpStatus {
    OK(200),
    FOUND(302),
    NOT_FOUND(404);

    private int code;

    HttpStatus(int code) {
        this.code = code;
    }
}
