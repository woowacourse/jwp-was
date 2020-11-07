package http;

public class HttpMethod {
    private final String httpMethod;

    private HttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public static HttpMethod from(String httpMethod) {
        return new HttpMethod(httpMethod);
    }

    public String get() {
        return httpMethod;
    }
}
