package webserver.http.request;

public class HttpRequest {
    private RequestStartLine requestStartLine;
    private RequestHeaders requestHeaders;
    private RequestBody requestBody;

    public HttpRequest(RequestStartLine requestStartLine, RequestHeaders requestHeaders, RequestBody requestBody) {
        validate(requestStartLine, requestBody);
        this.requestStartLine = requestStartLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    private void validate(RequestStartLine requestStartLine, RequestBody requestBody) {
        if (!requestStartLine.allowBody() && !requestBody.isEmpty()) {
            throw new IllegalArgumentException("이 HttpMethod에서는 body message가 포함되는 것이 허용되지 않습니다.");
        }
    }

    public RequestStartLine getHttpStartLine() {
        return requestStartLine;
    }

    public RequestHeaders getHttpHeaders() {
        return requestHeaders;
    }

    public RequestBody getHttpBody() {
        return requestBody;
    }
}
