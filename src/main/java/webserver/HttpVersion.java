package webserver;

public enum HttpVersion {
    USING_VERSION("HTTP/1.1");

    private final String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public String get() {
        return version;
    }
}
