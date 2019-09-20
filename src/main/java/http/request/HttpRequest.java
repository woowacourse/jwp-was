package http.request;

import http.common.HttpHeader;
import http.common.URL;

public class HttpRequest {
    private static final String CONTENT_TYPE = "Content-Type";
    private final RequestLine requestLine;
    private final HttpHeader httpHeader;
    private final RequestParameter requestParameter;
    private final String body;

    public HttpRequest(RequestLine requestLine, HttpHeader httpHeader, String body) {
        this.requestLine = requestLine;
        this.httpHeader = httpHeader;
        this.body = body;
        this.requestParameter = createRequestParameter();
    }

    private RequestParameter createRequestParameter() {
        String queryString = requestLine.getUrl().getQueryString();
        if (requestLine.getMethod().hasBody() && "application/x-www-form-urlencoded".equals(httpHeader.get(CONTENT_TYPE))) {
            queryString += "&" + body;
        }
        return new RequestParameter(queryString);
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public String getBody() {
        return body;
    }

    public String getPath() {
        URL url = requestLine.getUrl();
        return url.getPath();
    }

    public RequestParameter getRequestParameter() {
        return requestParameter;
    }
}
