package exception;

public class InvalidRequestBodyException extends RuntimeException {
    private static final String INVALID_PATH_MESSAGE = "Request Body의 값이 올바르지 않습니다";

    public InvalidRequestBodyException() {
        this(INVALID_PATH_MESSAGE);
    }

    public InvalidRequestBodyException(String message) {
        super(message);
    }
}
