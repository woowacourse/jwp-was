package exception;

public class InvalidRequestBodyException extends HttpRequestException {
    public InvalidRequestBodyException() {
        super("지원하지 않는 request body 형식입니다.");
    }
}
