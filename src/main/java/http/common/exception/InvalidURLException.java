package http.common.exception;

public class InvalidURLException extends RuntimeException {
    private static final String INVALID_URL_EXCEPTION_MESSAGE = "잘못된 URL 입니다.";

    public InvalidURLException() {
        super(INVALID_URL_EXCEPTION_MESSAGE);
    }
}
