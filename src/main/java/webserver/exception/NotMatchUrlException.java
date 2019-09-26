package webserver.exception;

public class NotMatchUrlException extends RuntimeException {
    public NotMatchUrlException(String message) {
        super(message);
    }
}
