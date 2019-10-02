package controller.exception;

public class MethodNotAllowedException extends RuntimeException {
    private static final String METHOD_NOT_ALLOWED_EXCEPTION_MESSAGE = "허용되지 않은 Method 입니다.";

    public MethodNotAllowedException() {
        super(METHOD_NOT_ALLOWED_EXCEPTION_MESSAGE);
    }
}
