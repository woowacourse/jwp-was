package http.common.exception;

public class InvalidHeaderLines extends RuntimeException {
    public static String MESSAGE = "올바르지 않은 헤더 입니다.";

    public InvalidHeaderLines() {
        super(MESSAGE);
    }
}
