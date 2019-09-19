package network;

public class HttpRequest {
    private Url url;
    private HttpMethod httpMethod;
    private HttpVersion httpVersion;
    private HttpRequestParams httpRequestParams;
    private HttpHeader httpHeader;

    public HttpRequest(final Url url,
                       final HttpMethod httpMethod,
                       final HttpVersion httpVersion,
                       final HttpRequestParams httpRequestParams,
                       final HttpHeader httpHeader) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.httpVersion = httpVersion;
        this.httpRequestParams = httpRequestParams;
        this.httpHeader = httpHeader;
    }

    public String getUrl() {
        return url.getFullUrl();
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpRequestParams getHttpRequestParams() {
        return httpRequestParams;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "url=" + url +
                ", httpMethod=" + httpMethod +
                ", httpVersion=" + httpVersion +
                ", httpRequestParams=" + httpRequestParams +
                ", httpHeader=" + httpHeader +
                '}';
    }
}