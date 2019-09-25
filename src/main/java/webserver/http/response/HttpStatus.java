package webserver.http.response;

public enum HttpStatus {
    OK(200),
    FOUND(302);

    private final int code;

    HttpStatus(final int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code + " " + this.name();
    }
}
