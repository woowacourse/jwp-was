package view.exception;

public class NotSupportedViewException extends RuntimeException {
    public NotSupportedViewException() {
        super("Not supported view");
    }
}
