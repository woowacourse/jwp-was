package exception;

public class IllegalHttpMethodException extends IllegalArgumentException {

    private static final String INVALID_METHOD_MESSAGE = "잘못된 HTTP Method 입니다.";

    public IllegalHttpMethodException() {
        super(INVALID_METHOD_MESSAGE);
    }
}
