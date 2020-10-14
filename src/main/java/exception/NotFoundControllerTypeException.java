package exception;

public class NotFoundControllerTypeException extends RuntimeException {

    public NotFoundControllerTypeException(String message) {
        super(message);
    }
}
