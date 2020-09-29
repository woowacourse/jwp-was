package webserver.http.response;

public enum HttpStatus {
    OK(200, "정상적으로 처리되었습니다."),
    FOUND(302, "페이지를 이동합니다."),
    BAD_REQUEST(400, "처리할 수 없는 요청입니다."),
    NOT_FOUND(404, "경로를 찾을 수 없습니다.");

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
