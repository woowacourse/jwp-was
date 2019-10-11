package webserver.session.exception;

public class InvalidatedSessionException extends RuntimeException {
    public InvalidatedSessionException() {
        super("Invalidate된 세션 입니다.");
    }
}
