package server.web;

public enum HeaderName {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location");

    private static final String TOKEN = ": ";
    private final String name;

    HeaderName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getHeader(String value) {
        return this.name + TOKEN + value;
    }

    public String getHeader(int value) {
        return this.name + TOKEN + value;
    }
}
