package webserver.exception;

public class NotFoundResourceException extends RuntimeException {
    private static final String NOT_FOUND_RESOURCE_EXCEPTION = "해당하는 자원을 찾을 수 없습니다.";

    public NotFoundResourceException() {
        super(NOT_FOUND_RESOURCE_EXCEPTION);
    }
}
