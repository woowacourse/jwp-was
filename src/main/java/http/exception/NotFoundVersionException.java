package http.exception;

public class NotFoundVersionException extends RuntimeException {
    private static final String NOT_FOUND_VERSION_EXCEPTION_MESSAGE = "찾을 수 없는 HTTP 버전입니다.";

    public NotFoundVersionException() {
        super(NOT_FOUND_VERSION_EXCEPTION_MESSAGE);
    }
}
