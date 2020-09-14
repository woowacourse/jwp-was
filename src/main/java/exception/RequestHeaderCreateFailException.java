package exception;

public class RequestHeaderCreateFailException extends CreateFailException {
    public RequestHeaderCreateFailException() {
        super("Request Header 생성이 실패했습니다.");
    }
}
