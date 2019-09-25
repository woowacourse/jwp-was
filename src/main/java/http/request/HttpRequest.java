package http.request;

import http.HttpMethod;
import http.MediaType;

public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpCookie httpCookie;
    private QueryParameter queryParameter;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(HttpRequestLine httpRequestLine, HttpRequestHeader httpRequestHeader,
                       QueryParameter queryParameter, HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpCookie = HttpCookie.of(findCookie());
        this.queryParameter = queryParameter;
        this.httpRequestBody = httpRequestBody;
    }

    private String findCookie() {
        if (httpRequestHeader.isContainKey("Cookie")) {
            return this.httpRequestHeader.getHeader("Cookie");
        }
        return "";
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public boolean isContainExtension() {
        return MediaType.isContain(httpRequestLine.getPath());
    }

    public byte[] getHttpRequestBody() {
        return httpRequestBody.getBody();
    }

    public String getQueryValue(String key) {
        return queryParameter.getValue(key);
    }

    public HttpMethod getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getUri() {
        return httpRequestLine.getPath();
    }

    public String getCookie(String key) {
        return this.httpCookie.getCookie(key);
    }
}
