package exception;

public class RequestBodyCreateFailException extends CreateFailException {
    public RequestBodyCreateFailException() {
        super("Request Body 생성이 실패했습니다.");
    }
}
