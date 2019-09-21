package http.exceptions;

public class NoSuchResource extends RuntimeException {
    public NoSuchResource() {
    }

    public NoSuchResource(String message) {
        super(message);
    }
}
