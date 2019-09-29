package webserver.http.session.exception;

public class InvalidSessionKeyException extends RuntimeException {
    public static final String MESSAGE = "유효하지 않은 Session Key 값입니다.";

    public InvalidSessionKeyException() {
        super(MESSAGE);
    }
}
