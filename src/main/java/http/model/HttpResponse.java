package http.model;

public class HttpResponse {
    private final StatusLine statusLine;
    private final ContentType contentType;
    private byte[] body;

    public HttpResponse(StatusLine statusLine, ContentType contentType, byte[] body) {
        this.statusLine = statusLine;
        this.contentType = contentType;
        this.body = body;
    }

    public HttpResponse(StatusLine statusLine, ContentType contentType) {
        this.statusLine = statusLine;
        this.contentType = contentType;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }
}
