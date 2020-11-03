package exception;

public class InvalidRedirectViewNameException extends RuntimeException {
    public InvalidRedirectViewNameException(final String viewName) {
        super(viewName + "은 Redirect view가 아닙니다.");
    }
}
