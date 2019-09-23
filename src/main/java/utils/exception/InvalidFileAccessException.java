package utils.exception;

public class InvalidFileAccessException extends RuntimeException {
    public InvalidFileAccessException() {
        super();
    }

    public InvalidFileAccessException(String message) {
        super(message);
    }
}
