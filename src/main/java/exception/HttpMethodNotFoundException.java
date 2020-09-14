package exception;

public class HttpMethodNotFoundException extends RuntimeException {
    public HttpMethodNotFoundException(String methodName) {
        super("처리할 수 없는 HTTP Method입니다 -> " + methodName);
    }
}
