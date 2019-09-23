package webserver.domain;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1");

    private final String protocol;

    HttpVersion(final String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return protocol;
    }
}
