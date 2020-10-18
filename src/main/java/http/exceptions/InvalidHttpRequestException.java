package exceptions;

public class InvalidHttpRequestException extends RuntimeException {
    private static final String INVALID_HTTP_REQUEST = "유효한 HTTP 요청이 아닙니다.";

    public InvalidHttpRequestException() {
        super(INVALID_HTTP_REQUEST);
    }

    public InvalidHttpRequestException(String message) {
        super(message);
    }
}
