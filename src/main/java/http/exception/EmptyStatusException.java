package http.exception;

public class EmptyStatusException extends RuntimeException {
    private static final String EMPTY_STATUS_EXCEPTION_MESSAGE = "Status Code 를 입력하지 않았습니다.";

    public EmptyStatusException() {
        super(EMPTY_STATUS_EXCEPTION_MESSAGE);
    }
}