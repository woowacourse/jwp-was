package exception;

public class NoMatchHttpMethodException extends RuntimeException {
    public NoMatchHttpMethodException() {
        super("Http Method 형식이 맞지 않습니다.");
    }
}
