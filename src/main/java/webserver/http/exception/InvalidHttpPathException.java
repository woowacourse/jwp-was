package webserver.http.exception;

public class InvalidHttpPathException extends InvalidHttpTypeException {
    public InvalidHttpPathException() {
        super("유효하지 않는 Path 입니다.");
    }
}
