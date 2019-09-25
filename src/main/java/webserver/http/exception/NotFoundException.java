package webserver.http.exception;

public class NotFoundException extends RuntimeException {
    public static String MESSAGE = "유효하지 않은 Url 요청입니다.";

    public NotFoundException() {
        super(MESSAGE);
    }
}
