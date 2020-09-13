package http.exception;

public class ResourceNotFoundException extends InvalidHttpRequestException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
