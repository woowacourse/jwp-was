package webserver.http.exception;

public class NoMatchHeaderFieldException extends RuntimeException {
    public NoMatchHeaderFieldException() {
        super("맞지 않는 Header Field 입니다.");
    }
}
