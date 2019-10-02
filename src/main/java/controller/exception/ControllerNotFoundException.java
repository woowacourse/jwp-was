package controller.exception;

public class ControllerNotFoundException extends RuntimeException {
    private static final String MESSAGE = "요청을 처리할 수 있는 controller가 없습니다.";

    public ControllerNotFoundException() {
        super(MESSAGE);
    }
}
