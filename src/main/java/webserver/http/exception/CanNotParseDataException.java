package webserver.http.exception;

public class CanNotParseDataException extends RuntimeException {
    public CanNotParseDataException() {
        super("Data 추출을 할 수 없습니다.");
    }
}
