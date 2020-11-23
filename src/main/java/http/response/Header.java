package http.response;

public class Header {
    private final String header;

    public Header(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return header;
    }
}
