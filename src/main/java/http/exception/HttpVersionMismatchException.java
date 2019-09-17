package http.exception;

public class HttpVersionMismatchException extends HttpException {
    public static final String MESSAGE = "해당하는 HTTP Version이 없습니다.";

    public HttpVersionMismatchException() {
        super(MESSAGE);
    }
}
