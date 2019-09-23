package controller.exception;

public class HttpMethodNotAllowedException extends RuntimeException {
    private static final String METHOD_NOT_ALLOWED_EXCEPTION_MESSAGE = "해당하는 메소드를 지원하지 않습니다.";

    public HttpMethodNotAllowedException() {
        super(METHOD_NOT_ALLOWED_EXCEPTION_MESSAGE);
    }
}