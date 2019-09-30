package webserver.http.exception;

public class NonexistentContentTypeException extends RuntimeException {
    public NonexistentContentTypeException() {
        super("존재하지 않는 Http Content Type 입니다.");
    }
}
