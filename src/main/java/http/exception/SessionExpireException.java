package http.exception;

public class SessionExpireException extends RuntimeException {
    private static final String ERROR_MESSAGE = "세션이 만료되었습니다.";

    public SessionExpireException() {
        super(ERROR_MESSAGE);
    }
}
