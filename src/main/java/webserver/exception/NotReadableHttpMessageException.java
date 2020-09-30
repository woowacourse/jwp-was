package webserver.exception;

public class NotReadableHttpMessageException extends BusinessException {
    public NotReadableHttpMessageException(String message) {
        super(message);
    }
}
