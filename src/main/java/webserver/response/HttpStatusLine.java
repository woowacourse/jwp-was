package webserver.response;

public class HttpStatusLine {
    private static final String DEFAULT_PROTOCOL_VERSION = "HTTP/1.1";

    private final String protocolVersion;
    private final StatusCode statusCode;

    public HttpStatusLine(String protocolVersion, StatusCode statusCode) {
        this.protocolVersion = protocolVersion;
        this.statusCode = statusCode;
    }

    public HttpStatusLine(StatusCode statusCode) {
        this(DEFAULT_PROTOCOL_VERSION, statusCode);
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
