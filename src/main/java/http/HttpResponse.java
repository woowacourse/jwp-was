package http;

public class HttpResponse {
    private final String header;
    private final byte[] body;

    public HttpResponse(String header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public HttpResponse(String header) {
        this(header, null);
    }

    public String getHeader() {
        return this.header;
    }

    public byte[] getBody() {
        return body;
    }
}
