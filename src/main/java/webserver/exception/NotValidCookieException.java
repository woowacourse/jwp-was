package webserver.exception;

public class NotValidCookieException extends IllegalArgumentException {
    public NotValidCookieException() {
        super();
    }

    public NotValidCookieException(String s) {
        super(s);
    }

    public NotValidCookieException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidCookieException(Throwable cause) {
        super(cause);
    }
}
