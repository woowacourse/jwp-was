package webserver.exception;

public class NotMatchUrlException extends RuntimeException {
    public NotMatchUrlException(String s) {
        super("URL Not Match ");
    }
}
