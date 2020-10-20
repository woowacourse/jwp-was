package webserver;

public enum EntityHeader implements HttpField {
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type");

    private final String header;

    EntityHeader(String header) {
        this.header = header;
    }

    @Override
    public String get() {
        return header;
    }
}
