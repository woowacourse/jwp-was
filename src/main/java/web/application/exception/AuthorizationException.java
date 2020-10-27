package web.application.exception;

public class AuthorizationException extends IllegalArgumentException {
    public AuthorizationException() {
        super("허가되지 않은 사용자입니다.");
    }
}
