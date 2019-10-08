package http.request;

public class NotSupportedHttpVersionException extends RuntimeException {
    public NotSupportedHttpVersionException() {
        super("지원되지 않는 HTTP Version 입니다.");
    }
}
