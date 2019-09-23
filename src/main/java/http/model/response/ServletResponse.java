package http.model.response;

import http.model.common.HttpHeaders;
import http.model.common.HttpProtocols;

public class ServletResponse {
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
        addHeader("Location", uri);
    }

    public void setCookie(String id) {
        addHeader("Set-Cooke", id);
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

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
