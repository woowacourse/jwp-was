package http;

public enum Header {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");

    public final String name;

    Header(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
