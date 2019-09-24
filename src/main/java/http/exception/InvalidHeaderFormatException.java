package http.exception;

public class InvalidHeaderFormatException extends RuntimeException {
    private static final String INVALID_HEADER_FORMAT_EXCEPTION = "올바르지 않은 헤더 형식입니다.";

    public InvalidHeaderFormatException() {
        super(INVALID_HEADER_FORMAT_EXCEPTION);
    }
}
