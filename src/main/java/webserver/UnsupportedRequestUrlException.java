package webserver;

public class UnsupportedRequestUrlException extends RequestException {
    public UnsupportedRequestUrlException(String requestUrl) {
        super(String.format("해당 URL에 대한 요청은 지원하지 않습니다. {'URL' : %s}", requestUrl));
    }
}
