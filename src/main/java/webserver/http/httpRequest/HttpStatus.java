package webserver.http.httpRequest;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");

    private final int code;
    private final String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static HttpStatus values(String inputCode) {
        int code = Integer.parseInt(inputCode);
        for (HttpStatus value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 상태코드 입니다.");
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getCodeAndMessage() {
        return code + " " + message;
    }
}
