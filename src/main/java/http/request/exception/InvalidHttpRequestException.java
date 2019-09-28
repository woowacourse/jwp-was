package http.request.exception;

public class InvalidHttpRequestException extends RuntimeException {
    private static final String INVALID_HTTP_REQUEST_ERROR_MESSAGE = "잘못된 HTTP 요청 입니다.";

    public InvalidHttpRequestException(RuntimeException cause) {
        super(INVALID_HTTP_REQUEST_ERROR_MESSAGE, cause);
    }
}
