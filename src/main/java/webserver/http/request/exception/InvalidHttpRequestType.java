package webserver.http.request.exception;

public class InvalidHttpRequestType extends RuntimeException {
    public static final String MESSAGE = "유효하지 않은 Url 입니다.";

    public InvalidHttpRequestType() {
        super(MESSAGE);
    }
}
