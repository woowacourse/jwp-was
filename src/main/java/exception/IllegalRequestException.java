package exception;

public class IllegalRequestException extends Exception {

    private static final String MESSAGE = "적절하지 않은 요청입니다.";

    public IllegalRequestException() {
        super(MESSAGE);
    }
}
