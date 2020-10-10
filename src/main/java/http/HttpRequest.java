package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import utils.IOUtils;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final RequestBody requestBody;

    public HttpRequest(final RequestLine requestLine, final HttpHeaders httpHeaders,
            final RequestBody requestBody) {
        this.requestLine = Objects.requireNonNull(requestLine, "request line이 존재하지 않습니다.");
        this.httpHeaders = Objects.requireNonNull(httpHeaders, "request headers가 존재하지 않습니다.");
        this.requestBody = requestBody;
    }

    public static HttpRequest from(final BufferedReader bufferedReader) throws IOException {
        List<String> requestLineAndHeader = IOUtils.readHeader(bufferedReader);
        RequestLine line = RequestLine.from(requestLineAndHeader.get(0));
        HttpHeaders httpHeaders = HttpHeaders.from(requestLineAndHeader.subList(1, requestLineAndHeader.size()));
        if (httpHeaders.hasContentLength()) {
            int contentLength = httpHeaders.getContentLength();
            RequestBody body = RequestBody.from(IOUtils.readBody(bufferedReader, contentLength));
            return new HttpRequest(line, httpHeaders, body);
        }
        return new HttpRequest(line, httpHeaders, null);
    }

    public boolean equalsMethod(final HttpMethod httpMethod) {
        return requestLine.equalsMethod(httpMethod);
    }

    public boolean equalsPath(final String path) {
        return requestLine.equalsPath(path);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Uri getUri() {
        return requestLine.getUri();
    }

    public String getHeader(final String key) {
        return httpHeaders.getHeader(key);
    }

    public String getBodyValue(final String key) {
        return requestBody.getValue(key);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", requestHeaders=" + httpHeaders +
                ", requestBody=" + requestBody +
                '}';
    }
}
