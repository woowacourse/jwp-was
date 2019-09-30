package webserver.http.exception;

public class NotFoundRequestCookieException extends RuntimeException {
    public NotFoundRequestCookieException() {
        super("Cannot find requested cookie");
    }
}
