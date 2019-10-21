package webserver.exception;

public class RequestHandlingFailException extends RuntimeException {
    private static final String MESSAGE = "요청을 처리할 수 없습니다.";

    public RequestHandlingFailException() {
        super(MESSAGE);
    }
}
