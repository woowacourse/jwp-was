package http.common.exception;

public class HttpHeaderNotFoundException extends RuntimeException {
    private static final String HTTP_HEADER_NOT_FOUND_EXCEPTION_MESSAGE = "존재하지 않는 HTTP Header 입니다.";

    public HttpHeaderNotFoundException() {
        super(HTTP_HEADER_NOT_FOUND_EXCEPTION_MESSAGE);
    }
}
