package exception;

public class InvalidRequestParamsException extends RuntimeException {
    private static final String INVALID_PARAMS_MESSAGE = "Request에 포함된 인자의 값이 올바르지 않습니다";
    public InvalidRequestParamsException() {
        this(INVALID_PARAMS_MESSAGE);
    }

    public InvalidRequestParamsException(String message) {
        super(message);
    }
}
