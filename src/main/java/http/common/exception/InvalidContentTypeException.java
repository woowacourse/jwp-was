package http.common.exception;

public class InvalidContentTypeException extends RuntimeException {
    private static final String INVALID_CONTENT_TYPE_EXCEPTION_MESSAGE = "잘못된 ContentType 입니다.";

    public InvalidContentTypeException() {
        super(INVALID_CONTENT_TYPE_EXCEPTION_MESSAGE);
    }
}
