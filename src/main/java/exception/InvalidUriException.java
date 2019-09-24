package exception;

public class InvalidUriException extends IllegalArgumentException {

    public static final String INVALID_URI_EXCEPTION = "올바르지 않은 URI입니다.";

    public InvalidUriException() {
        super(INVALID_URI_EXCEPTION);
    }
}
