package webserver.servlet;

public class ApplicationBusinessException extends RuntimeException {
    public ApplicationBusinessException(String message) {
        super(message);
    }
}
