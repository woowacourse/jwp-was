package webserver;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("지원하지 않는 요청입니다.");
    }

    public BadRequestException(String pattern) {
        super(String.format("지원하지 않는 요청입니다. (pattern: %s)", pattern));
    }
}
