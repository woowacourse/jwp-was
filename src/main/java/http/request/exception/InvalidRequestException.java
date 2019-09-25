package http.request.exception;

public class InvalidRequestException extends RuntimeException {
    private static final String INVALID_REQUEST_EXCEPTION = "잘못된 HTTP 요청입니다.";

    public InvalidRequestException() {
        super(INVALID_REQUEST_EXCEPTION);
    }
}
