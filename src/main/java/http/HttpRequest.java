package http;

import http.common.HttpHeader;
import http.request.RequestBody;

public class HttpRequest {
    // TODO: HttpRequestTest 구현

    private RequestLine requestLine;
    private HttpHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(final RequestLine requestLine, final HttpHeader requestHeader, final RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }
}
