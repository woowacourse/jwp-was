package http.exception;

public class InvalidHttpVersionException extends IllegalArgumentException {

    private static final String INVALID_HTTP_VERSION_MESSAGE = "잘못된 프로토콜 버전입니다.";

    public InvalidHttpVersionException() {
        super(INVALID_HTTP_VERSION_MESSAGE);
    }
}
