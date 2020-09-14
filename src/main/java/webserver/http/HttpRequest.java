package webserver.http;

public class HttpRequest {
    private HttpStartLine httpStartLine;
    private HttpHeaders httpHeaders;
    private HttpBody httpBody;

    public HttpRequest(HttpStartLine httpStartLine, HttpHeaders httpHeaders, HttpBody httpBody) {
        validate(httpStartLine, httpBody);
        this.httpStartLine = httpStartLine;
        this.httpHeaders = httpHeaders;
        this.httpBody = httpBody;
    }

    private void validate(HttpStartLine httpStartLine, HttpBody httpBody) {
        if (!httpStartLine.allowBody() && !httpBody.isEmpty()) {
            throw new IllegalArgumentException("이 HttpMethod에서는 body message가 포함되는 것이 허용되지 않습니다.");
        }
    }

    public HttpStartLine getHttpStartLine() {
        return httpStartLine;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }
}
