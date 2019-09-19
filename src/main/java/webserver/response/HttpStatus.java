package webserver.response;

public enum HttpStatus {
    OK(200),
    FOUND(302);

    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }
}
