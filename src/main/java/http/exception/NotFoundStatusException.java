package http.exception;

public class NotFoundStatusException extends RuntimeException {
    private static final String NOT_FOUND_STATUS_EXCEPTION_MESSAGE = "Http Status 를 찾을 수 없습니다.";

    public NotFoundStatusException() {
        super(NOT_FOUND_STATUS_EXCEPTION_MESSAGE);
    }
}
