package webserver.exception;

public class NotMatchUrlException extends RuntimeException {
    public NotMatchUrlException() {
        super("URL Not Match ");
    }
}
