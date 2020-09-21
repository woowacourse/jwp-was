package exception;

public class InvalidContentLengthException extends RuntimeException {
    public InvalidContentLengthException(String contentLength) {
        super("HTTP Header의 Content-Length의 값이 숫자가 아닙니다! -> " + contentLength);
    }
}
