package webserver.exception;

public class IncorrectProtocolException extends RuntimeException {
    public IncorrectProtocolException(String identifier) {
        super(String.format("%s는 올바르지 않은 프로토콜입니다.", identifier));
    }
}
