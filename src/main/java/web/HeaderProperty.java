package web;

public enum HeaderProperty {
    REQUEST_LINE("requestLine"),
    HOST("Host"),
    CONNECTION("Connection"),
    CONTENT_LENGTH("Content-Length"),
    ACCEPT("Accept");

    private String name;

    HeaderProperty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
