package exception;

public class DisabledEncodingException extends RuntimeException {
    public DisabledEncodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
