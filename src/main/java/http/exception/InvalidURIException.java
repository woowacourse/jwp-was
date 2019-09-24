package http.exception;

public class InvalidURIException extends RuntimeException {
    private static final String INVALID_URI_EXCEPTION_MESSAGE = "URI가 올바르지 않습니다.";

    public InvalidURIException() {
        super(INVALID_URI_EXCEPTION_MESSAGE);
    }
}