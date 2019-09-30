package http.response;

import http.HttpHeaders;
import http.HttpVersion;
import http.MediaType;

import static http.HttpHeaders.*;
import static http.response.HttpStatus.FOUND;
import static http.response.HttpStatus.OK;

public class HttpResponse {
    public static final String CRLF = "\r\n";

    private HttpVersion version;
    private HttpStatus status;
    private HttpHeaders headers;
    private byte[] body;

    private HttpResponse(HttpVersion version, HttpHeaders httpHeaders) {
        this.version = version;
        this.status = OK;
        this.headers = httpHeaders;
    }

    public static HttpResponse of(HttpVersion version) {
        return new HttpResponse(version, new HttpHeaders());
    }

    public void redirect(String location) {
        setStatus(FOUND);
        addHeader(LOCATION, location);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getMessageHeader() {
        return version + " " + status.getMessage() + CRLF
                + headers.toString();
    }

    public boolean hasBody() {
        return body != null;
    }

    public byte[] getBody() {
        return body;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setBody(byte[] body) {
        this.body = body;
        addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public void setBody(ResponseBody body) {
        byte[] content = body.getContent();
        String length = String.valueOf(content.length);
        MediaType type = body.getMediaType();

        this.body = content;
        addHeader(CONTENT_TYPE, type.getValue());
        addHeader(CONTENT_LENGTH, length);
    }
}
