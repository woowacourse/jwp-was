package http.request;

import http.common.ContentType;
import http.common.HttpHeader;
import http.common.URL;
import http.request.exception.InvalidHttpRequestException;

public class HttpRequest {
    static final String CONTENT_TYPE = "Content-Type";
    private final RequestLine requestLine;
    private final HttpHeader httpHeader;
    private final RequestParameter requestParameter;
    private final String body;

    public HttpRequest(RequestLine requestLine, HttpHeader httpHeader, String body) {
        checkValidHttpRequest(requestLine, httpHeader, body);
        this.requestLine = requestLine;
        this.httpHeader = httpHeader;
        this.body = body;
        this.requestParameter = createRequestParameter();
    }

    private void checkValidHttpRequest(RequestLine requestLine, HttpHeader httpHeader, String body) {
        if (requestLine == null || httpHeader == null || body == null) {
            throw new InvalidHttpRequestException();
        }
    }

    private RequestParameter createRequestParameter() {
        String queryString = requestLine.getUrl().getQueryString();
        if (requestLine.getMethod().hasBody() && ContentType.FORM_URLENCODED.getContentType().equals(httpHeader.get(CONTENT_TYPE))) {
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

    public RequestMethod getMethod() {
        return requestLine.getMethod();
    }
}
