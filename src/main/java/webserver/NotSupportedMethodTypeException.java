package webserver;

public class NotSupportedMethodTypeException extends RuntimeException {

    public NotSupportedMethodTypeException() {
        super("지원하지 않는 메소드 타입입니다.");
    }
}
