package webserver.servlet.exception;

public class HandlerNotFoundException extends NotFoundException {

    private static final String HANDLER_NOT_FOUND_EXCEPTION_MESSAGE_FORMAT = "해당 경로로 요청하는 자원을 처리할 수 없습니다. : %s";

    public HandlerNotFoundException(String requestPath) {
        super(String.format(HANDLER_NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, requestPath));
    }
}
