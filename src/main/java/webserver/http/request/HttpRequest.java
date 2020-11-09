package webserver.http.request;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final HttpRequestHeaders requestHeaders;
    private final HttpRequestParameters requestParameters;

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeaders requestHeaders,
            HttpRequestParameters requestParameters) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestParameters = requestParameters;
    }

    public boolean isStaticResourceRequest() {
        return requestLine.isStaticResourceRequest();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public String getParameter(String key) {
        return requestParameters.get(key);
    }
}
