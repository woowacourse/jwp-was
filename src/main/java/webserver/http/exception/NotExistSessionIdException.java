package webserver.http.exception;

public class NotExistSessionIdException extends RuntimeException {
    public NotExistSessionIdException() {
        super("존재하지 않는 세션 ID 입니다.");
    }
}
