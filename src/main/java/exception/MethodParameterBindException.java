package exception;

public class MethodParameterBindException extends RuntimeException {
    public MethodParameterBindException() {
        super("파라미터 매핑 예외");
    }
}
