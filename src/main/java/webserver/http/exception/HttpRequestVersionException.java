package webserver.http.exception;

public class HttpRequestVersionException extends RuntimeException {
    public HttpRequestVersionException() {
        super("Http Request Version 형식이 맞지 않습니다.");
    }
}
