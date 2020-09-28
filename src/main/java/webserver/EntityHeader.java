package webserver;

public enum EntityHeader {
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    CHARSET_UTF_8("charset=utf-8");

    private final String header;

    EntityHeader(String header) {
        this.header = header;
    }

    public boolean match(String line) {
        return line.contains(this.header);
    }

    public String get() {
        return header;
    }

    public String make(String value) {
        return this.header + ": " + value;
    }
}
