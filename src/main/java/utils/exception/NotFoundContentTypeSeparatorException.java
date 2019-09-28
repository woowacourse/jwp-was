package utils.exception;

public class NotFoundContentTypeSeparatorException extends RuntimeException {
    public NotFoundContentTypeSeparatorException() {
        super("Content-Type의 구분자('/')를 찾을 수 없습니다.");
    }
}