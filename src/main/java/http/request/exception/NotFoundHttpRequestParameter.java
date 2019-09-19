package http.request.exception;

public class NotFoundHttpRequestParameter extends RuntimeException {
    public NotFoundHttpRequestParameter(String key) {
        super(String.format("파라미터에 [%s] 가 존재하지 않습니다.", key));
    }
}
