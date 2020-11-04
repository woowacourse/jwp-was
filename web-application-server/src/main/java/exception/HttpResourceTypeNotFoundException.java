package exception;

public class HttpResourceTypeNotFoundException extends RuntimeException {
    public HttpResourceTypeNotFoundException(String fileExtension) {
        super("처리할 수 없는 파일 확장자입니다! -> " + fileExtension);
    }
}
