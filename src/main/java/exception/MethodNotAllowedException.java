package exception;

public class MethodNotAllowedException extends HttpRequestException {
    public MethodNotAllowedException() {
        super("올바른 핸들러 메서드를 발견하지 못했습니다.");
    }
}
