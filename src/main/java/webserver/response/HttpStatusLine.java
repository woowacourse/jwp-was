package webserver.response;

public class HttpStatusLine {
    private String protocolVersion;
    private final StatusCode statusCode;

    public HttpStatusLine(final String protocolVersion, final StatusCode statusCode) {
        this.protocolVersion = protocolVersion;
        this.statusCode = statusCode;
    }

    public HttpStatusLine(final StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setHttpProtocolVersion(final String httpProtocolVersion) {
        this.protocolVersion = httpProtocolVersion;
    }
}
