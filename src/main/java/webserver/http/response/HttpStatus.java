package webserver.http.response;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "FOUND");

    private final int code;
    private final String text;

    HttpStatus(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public String toHttpMessage() {
        return this.code + " " + this.text;
    }
}
