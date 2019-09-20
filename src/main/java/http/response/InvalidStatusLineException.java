package http.response;

public class InvalidStatusLineException extends RuntimeException {
    private static final String INVALID_STATUS_LINE_EXCEPTION_MESSAGE = "잘못된 Status line 입니다.";

    public InvalidStatusLineException() {
        super(INVALID_STATUS_LINE_EXCEPTION_MESSAGE);
    }
}
