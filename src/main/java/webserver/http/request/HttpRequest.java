package webserver.http.request;

import java.util.List;

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

    public RequestMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public String getUrl() {
        return getRequestUrl().getUrl();
    }

    public String getParameter(String key) {
        return getRequestParameter().getOneParameterValue(key);
    }

    public List<String> getAllParameter(String key) {
        return getRequestParameter().get(key);
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public RequestUrl getRequestUrl() {
        return requestLine.getHttpUrl();
    }

    public RequestParams getRequestParameter() {
        return getRequestUrl().getHttpRequestParams();
    }

    public RequestBody getHttpBody() {
        return requestBody;
    }
}
