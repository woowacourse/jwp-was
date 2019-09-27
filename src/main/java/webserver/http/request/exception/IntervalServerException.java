package webserver.http.request.exception;

public class IntervalServerException extends RuntimeException {
    public static final String MESSAGE = "잘못된 요청입니다.";

    public IntervalServerException() {
        super(MESSAGE);
    }
}
