package http.exception;

public class StartLineException extends HttpException {
    public static final String MESSAGE = "Start Line이 잘못되었습니다.";

    public StartLineException() {
        super(MESSAGE);
    }
}
