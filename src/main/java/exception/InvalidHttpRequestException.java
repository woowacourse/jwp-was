package exception;

public class InvalidHttpRequestException extends RuntimeException {
    private static final String INVALID_FILE_PATH_MASSAGE = "Http Request의 값이 올바르지 않습니다";

    public InvalidHttpRequestException() {
        this(INVALID_FILE_PATH_MASSAGE);
    }

    public InvalidHttpRequestException(String message) {
        super(message);
    }
}
