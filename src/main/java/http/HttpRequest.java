package http;

import java.util.List;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private final RequestBody requestBody;

    public HttpRequest(final RequestLine requestLine, final RequestHeaders requestHeaders,
            final RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    public static HttpRequest from(final List<String> header) {
        return new HttpRequest(RequestLine.from(header.get(0)), RequestHeaders.from(header.subList(1, header.size())),
                null);
    }

    public static HttpRequest from(final List<String> header, final String body) {
        return new HttpRequest(RequestLine.from(header.get(0)), RequestHeaders.from(header.subList(1, header.size())),
                RequestBody.from(body));
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
