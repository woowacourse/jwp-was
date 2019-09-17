package http.exception;

public class EmptyHttpRequestException extends HttpException {
    public static final String MESSAGE = "Http Request가 비어있습니다.";

    public EmptyHttpRequestException() {
        super(MESSAGE);
    }
}
