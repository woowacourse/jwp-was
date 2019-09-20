package http.response.exception;

public class InvalidHttpResponseException extends RuntimeException {
    public static final String INVALID_HTTP_RESPONSE_EXCEPTION_MESSAGE = "잘못된 HTTP response 입니다.";

    public InvalidHttpResponseException() {
        super(INVALID_HTTP_RESPONSE_EXCEPTION_MESSAGE);
    }
}
