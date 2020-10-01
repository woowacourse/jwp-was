package http.response;

public enum HttpStatus {
    OK(200),
    FOUND(302);

    private int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public String convertToString() {
        return code + " " + name();
    }
}
