package http.request.exception;

public class InvalidRequestLineException extends RuntimeException {
    public static final String MESSAGE = "올바르지 않은 Request Line 입니다.";

    public InvalidRequestLineException() {
        super(MESSAGE);
    }
}
