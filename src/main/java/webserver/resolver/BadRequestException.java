package webserver.resolver;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("지원하지 않는 요청입니다.");
    }
}
