package exception;

public class InvalidRequestLineException extends RuntimeException {
    private static final String INVALID_LINE_MESSAGE = "Request Line의 값이 올바르지 않습니다";

    public InvalidRequestLineException() {
        this(INVALID_LINE_MESSAGE);
    }

    public InvalidRequestLineException(String message) {
        super(message);
    }
}
