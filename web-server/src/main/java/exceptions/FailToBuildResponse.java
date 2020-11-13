package exceptions;

public class FailToBuildResponse extends RuntimeException {
    public FailToBuildResponse() {
    }

    public FailToBuildResponse(String message) {
        super(message);
    }
}
