package http;

import java.util.Objects;

public class RequestLine {
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int REQUEST_LINE_TOTAL_PARTS_COUNT = 3;

    private final HttpMethod method;
    private final Uri uri;
    private final String protocol;

    private RequestLine(final HttpMethod method, final Uri uri, final String protocol) {
        this.method = Objects.requireNonNull(method, "http 메서드가 존재하지 않습니다.");
        this.uri = Objects.requireNonNull(uri, "uri가 존재하지 않습니다.");
        this.protocol = Objects.requireNonNull(protocol, "protocol이 존재하지 않습니다.");
    }

    public static RequestLine from(final String requestLine) {
        String[] splitLine = requestLine.split(REQUEST_LINE_DELIMITER);
        if (splitLine.length != REQUEST_LINE_TOTAL_PARTS_COUNT) {
            throw new IllegalArgumentException("request line 형식이 올바르지 않습니다. request line count: " + splitLine.length);
        }
        return new RequestLine(HttpMethod.valueOf(splitLine[0]), Uri.from(splitLine[1]), splitLine[2]);
    }

    public boolean equalsMethod(final HttpMethod httpMethod) {
        return method.equals(httpMethod);
    }

    public boolean equalsPath(final String path) {
        return uri.equalsPath(path);
    }

    public String getPath() {
        return uri.getPath();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Uri getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", uri='" + uri + '\'' +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
