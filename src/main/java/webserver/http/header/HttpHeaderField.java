package webserver.http.header;

public enum HttpHeaderField {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie"),
    COOKIE("Cookie");

    private final String name;

    HttpHeaderField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
