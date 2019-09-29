package webserver.controller.exception;

public class MethodNotAllowedException extends RuntimeException {
    private static final String CODE = "405";
    private static final String MESSAGE = "Method Not Allowed";
    private static final String BLANK = " ";

    public MethodNotAllowedException() {
        super(CODE + BLANK + MESSAGE);
    }
}
