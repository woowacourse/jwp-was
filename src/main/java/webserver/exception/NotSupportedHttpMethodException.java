package webserver.exception;

public class NotSupportedHttpMethodException extends IllegalArgumentException {
    public NotSupportedHttpMethodException() {
        super();
    }

    public NotSupportedHttpMethodException(String s) {
        super(s);
    }

    public NotSupportedHttpMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportedHttpMethodException(Throwable cause) {
        super(cause);
    }
}
