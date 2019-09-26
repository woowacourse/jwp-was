package webserver.http.exception;

public class HttpRequestMethodException extends RuntimeException {
    public HttpRequestMethodException() {
        super("Http Request Method 형식이 맞지 않습니다.");
    }
}
