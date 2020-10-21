package exception;

public class NotFoundServletException extends HttpException {
    public NotFoundServletException(int exception) {
        super(String.format("%d는 지원하지 않는 예외 코드입니다.", exception));
    }
}
