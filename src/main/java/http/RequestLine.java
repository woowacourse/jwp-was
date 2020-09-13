package http;

public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private final HttpMethod method;
    private final Uri uri;
    private final String httpVersion;

    public RequestLine(final HttpMethod method, final Uri uri, final String httpVersion) {
        this.method = method;
        this.uri = uri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine from(final String requestLine) {
        String[] splitLine = requestLine.split(REQUEST_LINE_DELIMITER);
        return new RequestLine(HttpMethod.valueOf(splitLine[0]), Uri.from(splitLine[1]), splitLine[2]);
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

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", uri='" + uri + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                '}';
    }
}
