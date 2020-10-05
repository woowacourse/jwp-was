package webserver.response;

public enum HttpVersion {
    USING_VERSION("HTTP/1.1");

    private final String name;

    HttpVersion(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}
