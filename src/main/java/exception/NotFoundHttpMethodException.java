package exception;

public class NotFoundHttpMethodException extends IllegalArgumentException {

    public NotFoundHttpMethodException(String message) {
        super(message);
    }
}
