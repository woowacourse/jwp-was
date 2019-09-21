package http.response.exception;

public class InvalidContentTypeException extends RuntimeException {
    public static final String MESSAGE = "지원하지 않는 Content Type 입니다.";

    public InvalidContentTypeException() {
        super(MESSAGE);
    }
}
