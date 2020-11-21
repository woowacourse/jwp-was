package exception;

public class NotStaticResourceRequestException extends RuntimeException {
    public NotStaticResourceRequestException() {
        super("정적 리소스 요청이 아닙니다.");
    }
}
