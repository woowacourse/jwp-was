package controller.exception;

public class NotSupportMethod extends RuntimeException {
    public NotSupportMethod(String message) {
        super(message);
    }
}
