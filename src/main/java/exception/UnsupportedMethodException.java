package exception;

public class UnsupportedMethodException extends RuntimeException {
    private static final String UNSUPPORTED_METHOD_MESSAGE = "지원하지 않는 메소드 호출입니다.";

    public UnsupportedMethodException() {
        super(UNSUPPORTED_METHOD_MESSAGE);
    }
}
