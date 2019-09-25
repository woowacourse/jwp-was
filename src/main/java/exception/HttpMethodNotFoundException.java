package exception;

public class HttpMethodNotFoundException extends RuntimeException {
    public static final String HTTP_METHOD_NOT_FOUND_MESSAGE = "올바른 요청이 아닙니다.";

    public HttpMethodNotFoundException() {
        super(HTTP_METHOD_NOT_FOUND_MESSAGE);
    }
}
