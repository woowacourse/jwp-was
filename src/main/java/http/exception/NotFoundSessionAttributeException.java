package http.exception;

public class NotFoundSessionAttributeException extends RuntimeException {
    public NotFoundSessionAttributeException() {
        super("Not Found Session Attribute");
    }
}
