package http.excption;

public class NotFoundHeaderException extends RuntimeException {
    public NotFoundHeaderException(String message) {
        super(message);
    }
}
