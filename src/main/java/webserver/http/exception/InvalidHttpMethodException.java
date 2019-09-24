package webserver.http.exception;

public class InvalidHttpMethodException extends InvalidHttpTypeException {
    public InvalidHttpMethodException() {
        super("존재하지 않는 메소드 타입 입니다.");
    }
}