package http.request;

public class InvalidHttpRequestException extends RuntimeException {
    private static final String INVALID_HTTP_REQUEST_EXCEPTION_MESSAGE = "잘못된 HTTP 요청입니다.";

    public InvalidHttpRequestException() {
        super(INVALID_HTTP_REQUEST_EXCEPTION_MESSAGE);
    }
}
