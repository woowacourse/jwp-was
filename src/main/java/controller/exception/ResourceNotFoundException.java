package controller.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final String CODE = "404";
    private static final String MESSAGE = "Not Found";
    private static final String BLANK = " ";

    public ResourceNotFoundException() {
        super(CODE + BLANK + MESSAGE);
    }
}
