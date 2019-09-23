package http.excption;

public class NotSupportedHttpRequestException extends RuntimeException {
    public NotSupportedHttpRequestException(String message) {
        super(message);
    }
}
