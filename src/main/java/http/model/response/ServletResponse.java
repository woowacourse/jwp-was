package http.model.response;

import http.model.common.HttpHeaders;
import http.model.common.HttpProtocols;
import http.model.request.HttpUri;

import java.io.OutputStream;
import java.util.Map;

public class ServletResponse {
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String LOCATION = "Location";
    private final OutputStream outputStream;
    private HttpProtocols protocols;
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private HttpUri uri;

    public ServletResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.httpHeaders = new HttpHeaders();
        setHTTP1();
    }

    public void addHeader(String key, String value) {
        this.httpHeaders.addHeader(key, value);
    }

    public void redirect(String uri) {
        this.httpStatus = HttpStatus.FOUND;
        addHeader(LOCATION, uri);
    }

    private void setHTTP1() {
        this.setProtocols(HttpProtocols.HTTP1);
    }

    public void setCookie(String id) {
        addHeader(SET_COOKIE, id);
    }

    public boolean hasResource() {
        return uri != null;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public String getUri() {
        return uri.getResourceLocation();
    }

    public void setUri(String uri) {
        this.uri = new HttpUri(uri);
    }

    public String getHeader(String key) {
        return this.httpHeaders.getHeader(key);
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders.getHeaders();
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
}
