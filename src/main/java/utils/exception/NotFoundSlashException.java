package utils.exception;

public class NotFoundSlashException extends RuntimeException {
    public NotFoundSlashException() {
        super("유효하지 않는 Content-Type 입니다.");
    }
}
