package webserver;

public class InvalidRequestHeaderException extends RuntimeException {
    public InvalidRequestHeaderException() {
        super("지원하지 않는 request header 형식입니다.");
    }
}
