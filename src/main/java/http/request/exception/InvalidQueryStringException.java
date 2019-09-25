package http.request.exception;

public class InvalidQueryStringException extends RuntimeException {
    private static final String INVALID_QUERY_STRING_EXCEPTION_MESSAGE = "잘못된 QueryString입니다.";

    public InvalidQueryStringException() {
        super(INVALID_QUERY_STRING_EXCEPTION_MESSAGE);
    }
}
