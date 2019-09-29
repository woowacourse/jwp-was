package exception;

public class SessionInvalidException extends RuntimeException{
    private static final String SESSION_INVALID_ERROR_MESSAGE = "세션이 만료되었습니다.";

    public SessionInvalidException() {
        super(SESSION_INVALID_ERROR_MESSAGE);
    }
}
