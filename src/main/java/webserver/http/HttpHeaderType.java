package webserver.http;

public enum HttpHeaderType {
    CONTENT_TYPE("Content-Type: "),
    CONTENT_LENGTH("Content-Length: ");

    private final String type;

    HttpHeaderType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
