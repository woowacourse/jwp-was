package webserver.http.request;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeaders requestHeaders;
    private RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders, RequestBody requestBody) {
        validate(requestLine, requestBody);
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    private void validate(RequestLine requestLine, RequestBody requestBody) {
        if (!requestLine.allowBody() && !requestBody.isEmpty()) {
            throw new IllegalArgumentException("이 HttpMethod에서는 body message가 포함되는 것이 허용되지 않습니다.");
        }
    }

    public RequestLine getHttpStartLine() {
        return requestLine;
    }

    public RequestBody getHttpBody() {
        return requestBody;
    }
}
