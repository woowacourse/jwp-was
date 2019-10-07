package http;

public class NotAcceptableException extends RuntimeException {

    private NotAcceptableException(String format) {
        super(format);
    }

    public static NotAcceptableException from(String accept) {
        return new NotAcceptableException(String.format("서버에서 지원할 수 없는 컨텐트 타입입니다. [accept: %s]", accept));
    }
}
