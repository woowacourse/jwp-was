package exception;

public class RequestMethodNotSupportedException extends RuntimeException {
    private static final String METHOD_NOT_SUPPORTED_MESSAGE = " 요청을 지원하지 않습니다.";

    public RequestMethodNotSupportedException(String message) {
        super(message + METHOD_NOT_SUPPORTED_MESSAGE);
    }
}
