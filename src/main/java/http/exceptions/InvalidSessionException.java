package exceptions;

public class InvalidSessionException extends RuntimeException {
    private static final String INVALID_HTTP_REQUEST = "SESSION이 존재하지 않습니다.";

    public InvalidSessionException() {
        super(INVALID_HTTP_REQUEST);
    }

    public InvalidSessionException(String message) {
        super(message);
    }
}
