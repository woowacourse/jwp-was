package exception;

public class NoMatchHttpVersionException extends RuntimeException {
    public NoMatchHttpVersionException() {
        super("Http Version 형식이 맞지 않습니다.");
    }
}
