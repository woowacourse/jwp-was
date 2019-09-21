package http.common.exception;

public class IllegalHttpVersionException extends RuntimeException {
    public static final String MESSAGE = "올바르지 않은 Http 버전입니다.";

    public IllegalHttpVersionException() {
        super(MESSAGE);
    }
}
