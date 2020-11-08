package exception;

public class NotSupportedMethodException extends RuntimeException {
    public NotSupportedMethodException(String method) {
        super("해당 Method는 지원되지 않습니다: " + method);
    }
}
