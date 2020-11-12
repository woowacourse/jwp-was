package exceptions;

public class FailToHandleRequest extends RuntimeException {
    public FailToHandleRequest() {
    }

    public FailToHandleRequest(String message) {
        super(message);
    }
}
