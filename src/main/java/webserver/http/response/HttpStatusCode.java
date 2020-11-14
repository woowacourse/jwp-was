package webserver.http.response;

public enum HttpStatusCode {
    OK("200", "OK");

    private final String code;
    private final String message;

    HttpStatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
