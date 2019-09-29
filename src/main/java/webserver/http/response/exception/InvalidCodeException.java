package webserver.http.response.exception;

public class InvalidCodeException extends RuntimeException {
    public static final String MESSAGE = "유효하지 않은 상태 코드 입니다.";

    public InvalidCodeException() {
        super(MESSAGE);
    }
}
