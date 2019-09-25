package http;

public class NotSupportedHttpMethodException extends RuntimeException {
    public NotSupportedHttpMethodException() {
        super("지원하지 않는 메소드 타입입니다.");
    }

    public NotSupportedHttpMethodException(String pattern) {
        super(String.format("지원하지 않는 메소드 타입입니다. (pattern: %s)", pattern));
    }
}
