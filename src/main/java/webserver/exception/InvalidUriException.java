package webserver.exception;

public class InvalidUriException extends RuntimeException {
    private static final String INVALID_URI_EXCEPTION_MESSAGE = "해당하는 URI를 처리하는 컨트롤러가 없습니다.";

    public InvalidUriException() {
        super(INVALID_URI_EXCEPTION_MESSAGE);
    }
}