package controller.exception;

public class NotFoundUrlException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Cannot find requested url!";

    public NotFoundUrlException() {
        super(EXCEPTION_MESSAGE);
    }

    public NotFoundUrlException(Throwable cause) {
        super(EXCEPTION_MESSAGE, cause);
    }
}
