package http.common.exception;

public class HttpVersionNotFoundException extends RuntimeException {

    public static final String HTTP_VERSION_NOT_FOUND_EXCEPTION = "존재하지 않는 HTTP 버전입니다.";

    public HttpVersionNotFoundException() {
        super(HTTP_VERSION_NOT_FOUND_EXCEPTION);
    }
}
