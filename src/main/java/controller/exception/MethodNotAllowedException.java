package controller.exception;

public class MethodNotAllowedException extends RuntimeException {
    public static String MESSAGE = "지원하지 않는 메서드 요청입니다.";

    public MethodNotAllowedException() {
        super(MESSAGE);
    }
}
