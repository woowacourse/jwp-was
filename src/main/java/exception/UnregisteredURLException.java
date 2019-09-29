package exception;

public class UnregisteredURLException extends RuntimeException {
    private static final String UNREGISTERED_URL_MESSAGE = "잘못된 URL 요청입니다.";

    public UnregisteredURLException() {
        super(UNREGISTERED_URL_MESSAGE);
    }
}
