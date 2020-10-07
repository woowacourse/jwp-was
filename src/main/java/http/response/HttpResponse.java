package http.response;

import java.util.Map;

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

    public static HttpResponse of(ResponseHeader header, HttpStatus status, HttpVersion version, byte[] body) {
        return new HttpResponse(header, status, version, body);
    }

    public static HttpResponse of(ResponseHeader header, HttpStatus status, HttpVersion version) {
        return new HttpResponse(header, status, version, null);
    }

    public Map<String, String> getHeaders() {
        return header.getHeaders();
    }

    public String getHeader(String key) {
        return header.getHeader(key);
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
