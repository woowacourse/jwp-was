package exception;

public class RequestEntityCreateFailException extends CreateFailException {
    public RequestEntityCreateFailException() {
        super("Request Entity 생성이 실패했습니다.");
    }
}
