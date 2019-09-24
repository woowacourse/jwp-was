package exception;

public class NotFoundRequestElementException extends RuntimeException {
    private static final String REQUEST_ELEMENT_NOT_FOUND_ERROR_MESSAGE = "해당 요청의 정보를 찾을 수 없습니다.";

    public NotFoundRequestElementException() {
        super(REQUEST_ELEMENT_NOT_FOUND_ERROR_MESSAGE);
    }
}
