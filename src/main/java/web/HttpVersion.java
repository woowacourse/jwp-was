package web;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1");

    private final String version;

    HttpVersion(final String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
