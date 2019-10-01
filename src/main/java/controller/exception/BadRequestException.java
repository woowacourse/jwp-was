package controller.exception;

public class BadRequestException extends RuntimeException {
    private static final String CODE = "400";
    private static final String MESSAGE = "Bad Request";
    private static final String BLANK = " ";

    public BadRequestException() {
        super(CODE + BLANK + MESSAGE);
    }
}
