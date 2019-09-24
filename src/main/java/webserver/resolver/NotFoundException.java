package webserver.resolver;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("파일을 찾을 수 없습니다.");
    }
}
