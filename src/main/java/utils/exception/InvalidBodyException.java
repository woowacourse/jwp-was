package utils.exception;

public class InvalidBodyException extends RuntimeException {
    public static final String MESSAGE = "올바르지 않은 Body 입니다.";

    public InvalidBodyException() {
        super(MESSAGE);
    }
}
