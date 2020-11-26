package exception;

public class NotFoundStatusCodeException extends RuntimeException {

    public NotFoundStatusCodeException(String message) {
        super(message);
    }
}
