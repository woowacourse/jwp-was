package web.http;

public enum HttpStatus {
    OK(200, "정상적으로 처리되었습니다."),
    FOUND(302, "리다이렉트 합니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),
    NOT_FOUND(404, "해당 경로를 찾을 수 없습니다."),
    NOT_ALLOWED_METHOD(405, "지원하지 않는 HTTP 메소드 입니다.");

    private final int statusCode;
    private final String message;

    HttpStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
