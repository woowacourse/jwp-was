package http.response;

import http.HttpStatus;
import http.HttpVersion;

public class HttpResponse {

    private final ResponseHeader header;
    private final HttpStatus status;
    private final HttpVersion version;
    private byte[] body;

    private HttpResponse(ResponseHeader header, HttpStatus status, HttpVersion version, byte[] body) {
        this.header = header;
        this.status = status;
        this.version = version;
        this.body = body;
    }

    public static HttpResponse from(ResponseHeader header, HttpStatus status, HttpVersion version, byte[] body) {
        return new HttpResponse(header, status, version, body);
    }

    public static HttpResponse from(ResponseHeader header, HttpStatus status, HttpVersion version) {
        return new HttpResponse(header, status, version, null);
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HttpVersion getVersion() {
        return version;
    }

    public byte[] getBody() {
        return body;
    }
}
