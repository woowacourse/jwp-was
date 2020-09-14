package exception;

public class CreateFailException  extends RuntimeException {
    public CreateFailException(String message) {
        super(message);
    }
}
