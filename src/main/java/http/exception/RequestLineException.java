package http.exception;

public class RequestLineException extends HttpException {
    public static final String MESSAGE = "Request Line이 잘못되었습니다.";

    public RequestLineException() {
        super(MESSAGE);
    }
}
