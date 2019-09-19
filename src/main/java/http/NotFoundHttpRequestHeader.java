package http;

public class NotFoundHttpRequestHeader extends RuntimeException {
    public NotFoundHttpRequestHeader(String key) {
        super(String.format("헤더에 [%s] 가 존재하지 않습니다.", key));
    }
}
