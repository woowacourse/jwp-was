package exception;

public class InvalidRequestLineException extends RuntimeException {
    public InvalidRequestLineException(String line) {
        super("Request Line이 규격에 맞지 않습니다 -> " + line);
    }
}
