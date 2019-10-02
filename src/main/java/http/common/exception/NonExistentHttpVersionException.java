package http.common.exception;

public class NonExistentHttpVersionException extends RuntimeException {
    private static final String HTTP_VERSION_NOT_FOUND_EXCEPTION = "존재하지 않는 HTTP 버전입니다.";

    public NonExistentHttpVersionException() {
        super(HTTP_VERSION_NOT_FOUND_EXCEPTION);
    }
}
