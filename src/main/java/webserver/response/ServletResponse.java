package webserver.response;

import webserver.request.ServletRequest;

public class ServletResponse {
    private final HttpStatusLine statusLine;
    private final ResponseHeader headers;
    private final ResponseBody body;

    public ServletResponse(HttpStatusLine statusLine, ResponseHeader headers, ResponseBody body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public static ServletResponse of(ModelAndView mav, ServletRequest request) {
        return null;
    }

    public HttpStatusLine getStatusLine() {
        return statusLine;
    }

    public ResponseHeader getHeaders() {
        return headers;
    }

    public ResponseBody getBody() {
        return body;
    }

    public String getHeader(String key) {
        return headers.getHeader(key);
    }

    public String getAttribute(String key) {
        return body.getAttribute(key);
    }

    public StatusCode getStatusCode() {
        return statusLine.getStatusCode();
    }

    public String getProtocolVersion() {
        return statusLine.getProtocolVersion();
    }
}
