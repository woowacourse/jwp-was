package webserver.http.header;

public enum HttpHeaderName {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie"),
    COOKIE("Cookie");

    private final String name;

    HttpHeaderName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
