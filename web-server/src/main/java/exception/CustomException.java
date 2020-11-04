package exception;

public class CustomException extends RuntimeException {
    private final CustomExceptionStatus customExceptionStatus;

    public CustomException(CustomExceptionStatus customExceptionStatus) {
        super(customExceptionStatus.getMessage());
        this.customExceptionStatus = customExceptionStatus;
    }

    public CustomExceptionStatus getStatus() {
        return customExceptionStatus;
    }
}