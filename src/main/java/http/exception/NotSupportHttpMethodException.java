package http.exception;

public class NotSupportHttpMethodException extends RuntimeException {
    private static final String ERROR_MESSAGE = "지원하지 않는 HTTP Method 입니다.";

    public NotSupportHttpMethodException() {
        super(ERROR_MESSAGE);
    }
}
