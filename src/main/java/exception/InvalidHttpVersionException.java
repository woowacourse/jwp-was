package exception;

public class InvalidHttpVersionException extends RuntimeException {
    public InvalidHttpVersionException(String version) {
        super("처리할 수 없는 HTTP version입니다! -> " + version);
    }
}
