package exception;

public class InvalidRequestPathException extends RuntimeException {
    private static final String INVALID_PATH_MESSAGE = "잘못된 경로 요청입니다";
    
    public InvalidRequestPathException() {
        this(INVALID_PATH_MESSAGE);
    }

    public InvalidRequestPathException(String message) {
        super(message);
    }
}
