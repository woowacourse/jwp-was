package http;

public enum HttpStatusCode {
    OK(200, "OK"),
    FOUND(302, "FOUND"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_FOUND(404, "Not Found");


    private int code;
    private String message;

    HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String findCodeAndMessage() {
        return code + " " + message;
    }
}
