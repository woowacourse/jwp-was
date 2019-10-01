package exception;

public class MethodNotAllowedException extends RuntimeException {
    public MethodNotAllowedException() {
        super();
    }

    public MethodNotAllowedException(String message) {
        super(message);
    }
}
