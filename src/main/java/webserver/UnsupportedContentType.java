package webserver;

public class UnsupportedContentType extends RuntimeException {
    public UnsupportedContentType(String contentType) {
        super("지원되지 않는 요청 형식입니다: '" + contentType + "'");
    }
}
