package http.exception;

public class NotFoundHeaderException extends RuntimeException {
    public NotFoundHeaderException() {
        super("Not Found Header");
    }
}
