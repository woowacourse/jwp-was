package http;

public class IllegalSessionStateException extends RuntimeException {
    public IllegalSessionStateException() {
        super("invalidate 된 세션입니다.");
    }
}
