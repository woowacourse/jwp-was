package webserver.http.exception;

public class InvalidHttpVersionException extends InvalidHttpTypeException {
    public InvalidHttpVersionException() {
        super("유효하지 않는 HTTP 버전 입니다.");
    }
}
