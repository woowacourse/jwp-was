package exception;

public class InvalidRequestPathException extends RuntimeException {
    private static final String INVALID_PATH_MESSAGE = "Request Path의 값이 올바르지 않습니다";
    
    public InvalidRequestPathException() {
        this(INVALID_PATH_MESSAGE);
    }

    public InvalidRequestPathException(String message) {
        super(message);
    }
}
