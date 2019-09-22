package http.exceptions;

public class IllegalRequestMappingException extends RuntimeException {
    public IllegalRequestMappingException() {
    }

    public IllegalRequestMappingException(String message) {
        super(message);
    }
}
