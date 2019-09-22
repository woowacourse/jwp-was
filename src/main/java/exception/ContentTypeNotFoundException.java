package exception;

public class ContentTypeNotFoundException extends RuntimeException {
    public static final String CONTENT_TYPE_NOT_FOUND_MESSAGE = "ContentType을 찾을 수 없습니다.";

    public ContentTypeNotFoundException() {
        super(CONTENT_TYPE_NOT_FOUND_MESSAGE);
    }
}
