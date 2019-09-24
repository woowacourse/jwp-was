package http.model;

public class HttpResponse {
    private HttpProtocols protocol;
    private HttpStatus httpStatus;
    private ContentType contentType;
    private byte[] body;

    public HttpResponse(HttpProtocols protocol, HttpStatus httpStatus, ContentType contentType, byte[] body) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.body = body;
    }

    public HttpResponse(HttpProtocols protocol, HttpStatus httpStatus, ContentType contentType) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.contentType = contentType;
    }

    public HttpProtocols getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }
}
