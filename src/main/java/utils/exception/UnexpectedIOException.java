package utils.exception;

public class UnexpectedIOException extends RuntimeException {
    public UnexpectedIOException() {
        super();
    }

    public UnexpectedIOException(String message) {
        super(message);
    }
}
