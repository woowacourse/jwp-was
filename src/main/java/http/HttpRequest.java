package http;

public class HttpRequest {
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private String body;

    HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public HttpUrl getUrl() {
        return requestLine.getUrl();
    }

    public HttpVersion getVersion() {
        return requestLine.getVersion();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public QueryParams getQueryParams() {
        HttpMethod method = requestLine.getMethod();

        if (HttpMethod.GET.match(method)) {
            return requestLine.getUrl().getQueryParams();
        }
        if (HttpMethod.POST.match(method)) {
            return new QueryParams(body);
        }
        return null;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return requestLine + "\n" + headers + "\n";
    }
}
