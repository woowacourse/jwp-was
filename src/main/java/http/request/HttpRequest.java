package http.request;

public class HttpRequest {
    private final HttpStartLine httpStartLine;
    private final HttpRequestHeader httpRequestHeader;
    private final HttpCookie httpCookie;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(HttpStartLine httpStartLine, HttpRequestHeader httpRequestHeader,
                       HttpCookie httpCookie, HttpRequestBody httpRequestBody) {
        this.httpStartLine = httpStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpCookie = httpCookie;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequest(HttpStartLine httpStartLine, HttpRequestHeader header, HttpCookie httpCookie) {
        this(httpStartLine, header, httpCookie, new HttpRequestBody(""));
    }

    public String getMethod() {
        return httpStartLine.getMethod();
    }

    public String getHeader(String key) {
        return httpRequestHeader.getHeader(key);
    }

    public String getCookie(String key) {
        return httpCookie.getValue(key);
    }

    public String getResourcePath() {
        return httpStartLine.getResourcePath();
    }

    public String getParameter(String key) {
        String method = httpStartLine.getMethod();
        if ("GET".equals(method)) {
            return httpStartLine.getParameter(key);
        }
        return httpRequestBody.getParameter(key);
    }
}
