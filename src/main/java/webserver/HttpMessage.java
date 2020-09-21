package webserver;

public enum HttpMessage {
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    LOCATION("Location"),
    HTTP_VERSION("HTTP/1.1"),
    STATUS_OK("200 OK"),
    STATUS_FOUND("302 FOUND");

    private final String name;

    HttpMessage(String name) {
        this.name = name;
    }

    public boolean isMatch(String header) {
        return header.contains(this.name);
    }

    public String getName() {
        return name;
    }
}
