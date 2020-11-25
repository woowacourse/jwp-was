package was.webserver.http.session.exception;

public class InvalidHttpSessionException extends RuntimeException {
    public InvalidHttpSessionException() {
        super("유효하지 않은 세션입니다.");
    }
}
