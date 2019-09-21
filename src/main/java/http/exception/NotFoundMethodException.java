package http.exception;

public class NotFoundMethodException extends RuntimeException {
    private static final String NOT_FOUND_METHOD_EXCEPTION_MESSAGE = "Http Method 를 찾을 수 없습니다.";

    public NotFoundMethodException() {
        super(NOT_FOUND_METHOD_EXCEPTION_MESSAGE);
    }
}