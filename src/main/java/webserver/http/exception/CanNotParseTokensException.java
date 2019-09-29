package webserver.http.exception;

public class CanNotParseTokensException extends RuntimeException {
    public CanNotParseTokensException() {
        super("파싱을 할 수 없습니다.");
    }
}
