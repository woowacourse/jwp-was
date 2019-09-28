package http.request.exception;

public class InvalidRequestLineException extends RuntimeException {
    private static final String INVALID_REQUEST_LINE_ERROR_MESSAGE = "잘못된 request line 입니다.";

    public InvalidRequestLineException() {
        super(INVALID_REQUEST_LINE_ERROR_MESSAGE);
    }

    public InvalidRequestLineException(Throwable cause) {
        super(INVALID_REQUEST_LINE_ERROR_MESSAGE, cause);
    }
}
