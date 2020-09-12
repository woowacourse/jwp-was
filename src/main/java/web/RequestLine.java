package web;

import java.util.Map;
import java.util.Objects;

public class RequestLine {
    private static final String REQUEST_LINE_DELIMITER = " ";

    private final String method;
    private final RequestUri requestUri;
    private final String protocol;

    public RequestLine(String requestLine) {
        String[] requests = requestLine.split(REQUEST_LINE_DELIMITER);
        this.method = requests[0];
        this.requestUri = new RequestUri(requests[1]);
        this.protocol = requests[2];
    }

    public boolean isStaticFile() {
        return this.requestUri.isStaticFile();
    }

    public String getPath() {
        return this.requestUri.getPath();
    }

    public Map<String, String> getQueryParams() {
        return this.requestUri.getQueryParams();
    }

    public String getMethod() {
        return method;
    }

    public RequestUri getRequestUri() {
        return requestUri;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(requestUri, that.requestUri) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, requestUri, protocol);
    }
}
