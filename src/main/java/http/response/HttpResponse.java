package http.response;

import http.HttpVersion;
import http.request.HttpRequest;

import java.util.Set;

public class HttpResponse {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";

    private StatusCode statusCode;
    private HttpVersion httpVersion;
    private HttpResponseHeader header;
    private byte[] body;

    public HttpResponse(HttpRequest httpRequest) {
        this.httpVersion = httpRequest.getVersion();
        this.header = new HttpResponseHeader(httpRequest.getCookie());
    }

    public void okResponse(String contentType, byte[] body) {
        this.statusCode = StatusCode.OK;
        header.addHeader(CONTENT_TYPE, String.format("text/%s; charset=utf-8", contentType));
        this.body = body;
        header.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public void redirect(String location) {
        this.statusCode = StatusCode.FOUND;
        header.addHeader(LOCATION, location);
    }

    public void badRequest() {
        this.statusCode = StatusCode.BAD_REQUEST;
    }

    public void notFound() {
        this.statusCode = StatusCode.NOT_FOUND;
    }

    public void methodNotAllow() {
        this.statusCode = StatusCode.METHOD_NOT_FOUND;
    }

    public void internalServerError() {
        this.statusCode = StatusCode.INTERNAL_SERVER_ERROR;
    }

    public byte[] getBody() {
        return this.body;
    }

    public boolean hasCookie() {
        return header.hasCookie();
    }

    public String getCookieValues() {
        return header.getCookieValues();
    }

    public HttpVersion getVersion() {
        return httpVersion;
    }

    public StatusCode getStatus() {
        return statusCode;
    }

    public Set<String> getHeaderKeys() {
        return header.getKeySet();
    }

    public String getHeaderValue(String key) {
        return header.getHeader(key);
    }
}
