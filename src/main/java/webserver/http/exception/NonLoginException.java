package webserver.http.exception;

public class NonLoginException extends RuntimeException {
    public NonLoginException() {
        super("로그인 상태가 아닙니다.");
    }
}
