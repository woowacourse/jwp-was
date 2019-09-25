package http.request.exception;

public class InvalidRequestMethodException extends RuntimeException {
    private static final String INVALID_REQUEST_METHOD_EXCEPTION_MESSAGE = "존재하지 않는 HTTP Method입니다.";

    public InvalidRequestMethodException() {
        super(INVALID_REQUEST_METHOD_EXCEPTION_MESSAGE);
    }
}
