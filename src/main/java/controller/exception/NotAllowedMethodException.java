package controller.exception;

public class NotAllowedMethodException extends RuntimeException {
    public static String MESSAGE = "지원하지 않는 메서드 요청입니다.";

    public NotAllowedMethodException() {
        super(MESSAGE);
    }
}
