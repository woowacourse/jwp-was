package exception;

public class NotFoundUrlException extends RuntimeException {
    private static final String PATH_NOT_FOUND_ERROR_MESSAGE = "알맞은 URL을 찾지 못했습니다.";

    public NotFoundUrlException() {
        super(PATH_NOT_FOUND_ERROR_MESSAGE);
    }
}
