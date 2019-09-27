package http.response;

import http.HttpHeaders;
import http.HttpMimeType;
import http.HttpVersion;

import static http.HttpHeaders.*;
import static http.response.HttpStatus.FOUND;
import static http.response.HttpStatus.OK;

public class HttpResponse {
    private HttpVersion version;
    private HttpStatus status;
    private HttpHeaders headers;
    private byte[] body;

    public HttpResponse(HttpVersion version) {
        this.version = version;
        this.status = OK;
        this.headers = new HttpHeaders();
    }

    public void redirect(String location) {
        status = FOUND;
        headers.put(LOCATION, location);
        body = null;
    }

    public void error(HttpStatus status) {
        this.status = status;
        headers.clear();
        setBody(status.getMessage().getBytes(), HttpMimeType.HTML);
    }

    public HttpVersion getVersion() {
        return version;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setBody(byte[] body, HttpMimeType mediaType) {
        if (body != null) {
            headers.put(CONTENT_TYPE, mediaType.toString());
            headers.put(CONTENT_LENGTH, Integer.toString(body.length));
            this.body = body;
        }
    }

    public byte[] getBody() {
        return body;
    }
}
