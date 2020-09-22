package http;

public enum HttpMethod {
    GET,
    POST;

    public static HttpMethod from(String method) {
        try {
            return valueOf(method);
        } catch (IllegalArgumentException ignored){
            throw new IllegalArgumentException("지원하지 않는 방법 입니다.");
        }
    }
}
