package webserver.exception;

public class HandlerNotFoundException extends RuntimeException {
    private static final String HANDLER_NOT_FOUND_EXCEPTION_ERROR_MESSAGE = "Handler를 찾을 수 없습니다.";

    public HandlerNotFoundException() {
        super(HANDLER_NOT_FOUND_EXCEPTION_ERROR_MESSAGE);
    }
}
