package http.request;

import http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private HttpRequestStartLine httpRequestStartLine;
    private HttpHeader httpHeader;
    private HttpBody httpBody;

    public HttpRequest(HttpRequestStartLine httpRequestStartLine, HttpHeader httpHeader, HttpBody httpBody) throws IOException {
       this.httpRequestStartLine = httpRequestStartLine;
       this.httpHeader = httpHeader;
       this.httpBody = httpBody;
    }

    public boolean hasParameters() {
        return httpRequestStartLine.hasParameters();
    }

    public boolean hasBody() {
        return !httpBody.equals(HttpBody.EMPTY_BODY);
    }

    public HttpMethod getHttpMethod() {
        return httpRequestStartLine.getHttpMethod();
    }

    public String getPath() {
        return httpRequestStartLine.getPath();
    }

    public String getHeader(String key) {
        return httpHeader.getHeader(key);
    }

    public String getParameter(String key) {
        return httpRequestStartLine.getParameter(key);
    }

    public HttpBody getBody() {
        return httpBody;
    }
}
