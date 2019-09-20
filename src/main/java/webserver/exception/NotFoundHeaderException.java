package webserver.exception;

public class NotFoundHeaderException extends RuntimeException {
    public NotFoundHeaderException() {
        super("Not Found Header");
    }
}
