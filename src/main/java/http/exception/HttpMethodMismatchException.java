package http.exception;

public class HttpMethodMismatchException extends HttpException {
    public static final String MESSAGE = "해당하는 HTTP Method가 없습니다.";

    public HttpMethodMismatchException() {
        super(MESSAGE);
    }
}
