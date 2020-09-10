package exception;

public class NotExistRequestHeader extends IllegalArgumentException {

    public NotExistRequestHeader(String message) {
        super(message);
    }
}
