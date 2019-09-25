package http.common.exception;

public class InvalidHeaderKeyException extends RuntimeException {
    private static final String INVALID_HEADER_KEY_EXCEPTION_MESSAGE = "잘못된 헤더 접근 key 입니다.";

    public InvalidHeaderKeyException() {
        super(INVALID_HEADER_KEY_EXCEPTION_MESSAGE);
    }
}
