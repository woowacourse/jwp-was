package webserver.http.exception;

public class NonexistentSessionValue extends RuntimeException {
    public NonexistentSessionValue() {
        super("존재하지 않는 세션 값입니다.");
    }
}
