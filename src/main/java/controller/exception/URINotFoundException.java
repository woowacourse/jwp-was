package controller.exception;

public class URINotFoundException extends RuntimeException {
    private static final String URI_NOT_FOUND_EXCEPTION_MESSAGE = "해당하는 메소드의 URI가 존재하지 않습니다.";

    public URINotFoundException() {
        super(URI_NOT_FOUND_EXCEPTION_MESSAGE);
    }
}