package http.model.response;

import http.model.common.HttpHeaders;
import http.model.common.HttpProtocols;

import java.io.OutputStream;
import java.util.Map;

public class ServletResponse {
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String LOCATION = "Location";
    private final OutputStream outputStream;
    private HttpProtocols protocols;
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private String view;

    public ServletResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.httpHeaders = new HttpHeaders();
        setHTTP1();
    }

    public void ok(String resource) {
        this.httpStatus = HttpStatus.OK;
        this.view = resource;
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

    public void setCookie(String value) {
        addHeader(SET_COOKIE, value);
    }

    public boolean hasResource() {
        return view != null;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
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

    public void unauthorized() {
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }
}
