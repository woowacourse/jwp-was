package webserver.http.request;

import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    public static final String HEADER_REQUEST_COOKIE = "Cookie";

    private RequestLine requestLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(RequestLine requestLine, HttpRequestHeader httpRequestHeader) {
        this.requestLine = requestLine;
        this.httpRequestHeader = httpRequestHeader;
    }

    public HttpRequest(final RequestLine requestLine, final HttpRequestHeader httpRequestHeader,
                       final HttpRequestBody httpRequestBody) {
        this.requestLine = requestLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public String getRequestLineElement(String elementKey) {
        return requestLine.getElementValue(elementKey);
    }

    public String getRequestHeaderElement(String elementKey) {
        return httpRequestHeader.getRequestElement(elementKey);
    }

    public Map<String, String> getHttpRequestBody() {
        if (Objects.nonNull(httpRequestBody)) {
            return httpRequestBody.getBody();
        }
        throw new NullPointerException();
    }

    public boolean hasCookieValue(String cookieKey) {
        return httpRequestHeader.isCookieValue(cookieKey);
    }

    public String getCookieValue() {
        return httpRequestHeader.getRequestElement(HEADER_REQUEST_COOKIE).split("=")[1];
    }
}
