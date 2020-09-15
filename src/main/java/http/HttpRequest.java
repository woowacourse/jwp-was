package http;

import java.util.List;
import java.util.Objects;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private final RequestBody requestBody;

    private HttpRequest(final RequestLine requestLine, final RequestHeaders requestHeaders,
            final RequestBody requestBody) {
        this.requestLine = Objects.requireNonNull(requestLine, "request line이 존재하지 않습니다.");
        this.requestHeaders = Objects.requireNonNull(requestHeaders, "request headers가 존재하지 않습니다.");
        this.requestBody = requestBody;
    }

    public static HttpRequest from(final String line, final List<String> header) {
        return new HttpRequest(RequestLine.from(line), RequestHeaders.from(header), null);
    }

    public static HttpRequest from(final String line, final List<String> header, final String body) {
        return new HttpRequest(RequestLine.from(line), RequestHeaders.from(header), RequestBody.from(body));
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
