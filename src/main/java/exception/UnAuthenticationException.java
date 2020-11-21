package exception;

public class UnAuthenticationException extends IllegalArgumentException {

    public UnAuthenticationException(String message) {
        super(message);
    }

}
