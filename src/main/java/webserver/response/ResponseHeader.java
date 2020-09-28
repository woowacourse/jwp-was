package webserver.response;

public enum ResponseHeader {
    LOCATION("Location");

    private final String header;

    ResponseHeader(String header) {
        this.header = header;
    }

    public String make(String value) {
        return header + ": " + value;
    }
}
