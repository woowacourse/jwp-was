package http.model.response;

import http.model.common.HttpHeaders;
import http.model.common.HttpProtocols;

import java.util.Map;

public class ServletResponse {
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String LOCATION = "Location";
    private HttpProtocols protocols;
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private byte[] body;

    public ServletResponse() {
        this.httpHeaders = new HttpHeaders();
    }

    public void addHeader(String key, String value) {
        this.httpHeaders.addHeader(key, value);
    }

    public void redirect(String uri) {
        this.httpStatus = HttpStatus.FOUND;
        addHeader(LOCATION, uri);
    }

    public void setCookie(String id) {
        addHeader(SET_COOKIE, id);
    }

    public HttpProtocols getProtocols() {
        return protocols;
    }

    public void setProtocols(HttpProtocols protocols) {
        this.protocols = protocols;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders.getHeaders();
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
