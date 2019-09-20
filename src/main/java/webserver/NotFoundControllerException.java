package webserver;

public class NotFoundControllerException extends RuntimeException {
    public NotFoundControllerException() {
        super("지원하지 않는 컨트롤러 입니다.");
    }
}
