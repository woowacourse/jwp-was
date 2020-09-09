package webserver.domain.request;

public class RequestLine {
    private final Method method;
    private final Uri uri;
    private final HttpVersion version;

    public RequestLine(Method method, Uri uri, HttpVersion version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }
}
