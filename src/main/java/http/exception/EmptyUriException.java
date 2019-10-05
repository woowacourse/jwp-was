package http.exception;

public class EmptyUriException extends RuntimeException {
    private static final String EMPTY_URI_EXCEPTION_MESSAGE = "uri는 비어있을 수 없습니다.";

    public EmptyUriException() {
        super(EMPTY_URI_EXCEPTION_MESSAGE);
    }
}
