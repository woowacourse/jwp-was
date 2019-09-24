package http.exception;

public class NotSupportContentTypeException extends RuntimeException {
    public static final String ERROR_MESSAGE = "지원하지 않는 Content-Type입니다.";

    public NotSupportContentTypeException() {
        super(ERROR_MESSAGE);
    }
}
