package http;

public enum  HttpMethod {
    GET,
    POST;

    public static HttpMethod from(String httpMethod) {
        return valueOf(httpMethod);
    }

    public HttpMethod get() {
        return this;
    }
}
