package http.supoort;

public class IllegalRequestMappingException extends RuntimeException {
    public IllegalRequestMappingException() {
    }

    public IllegalRequestMappingException(String message) {
        super(message);
    }
}
