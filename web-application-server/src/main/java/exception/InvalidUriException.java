package exception;

public class InvalidUriException extends RuntimeException {
    public InvalidUriException(String uri) {
        super("규격에 맞지 않는 URI입니다! -> " + uri);
    }
}
