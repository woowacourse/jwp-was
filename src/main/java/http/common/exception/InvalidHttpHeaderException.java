package http.common.exception;

public class InvalidHttpHeaderException extends RuntimeException {
    private static final String INVALID_HTTP_HEADER_EXCETPION_MESSAGE = "잘못된 Header입니다.";

    public InvalidHttpHeaderException() {
        super(INVALID_HTTP_HEADER_EXCETPION_MESSAGE);
    }
}
