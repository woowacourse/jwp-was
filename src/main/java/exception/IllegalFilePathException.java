package exception;

public class IllegalFilePathException extends IllegalArgumentException {
    public IllegalFilePathException(final Exception e) {
        super(e);
    }
}
