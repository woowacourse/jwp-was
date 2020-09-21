package webserver.response;

public class HttpStatusLine {
    private static final String DEFAULT_PROTOCOL_VERSION = "HTTP/1.1";

    private final String protocolVersion;
    private final StatusCode statusCode;

    public HttpStatusLine(final String protocolVersion, final StatusCode statusCode) {
        this.protocolVersion = protocolVersion;
        this.statusCode = statusCode;
    }

    public HttpStatusLine(final StatusCode statusCode) {
        this.protocolVersion = DEFAULT_PROTOCOL_VERSION;
        this.statusCode = statusCode;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
