package exception;

public class NotFoundControllerType extends RuntimeException {

    public NotFoundControllerType(String message) {
        super(message);
    }
}
