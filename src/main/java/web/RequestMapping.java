package web;

public class RequestMapping {
    private final String uri;
    private final HttpMethod httpMethod;

    public RequestMapping(final String uri, final HttpMethod httpMethod) {
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    public boolean match(HttpRequest httpRequest) {
        return this.uri.equals(httpRequest.getRequestPath()) && this.httpMethod == httpRequest.getMethod();
    }
}
