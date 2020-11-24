package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import http.ContentType;
import http.Cookie;
import http.HttpHeaders;
import http.HttpStatus;

public class HttpResponse {
    private static final String DEFAULT_PROTOCOL_VERSION = "HTTP/1.1";
    private static final HttpStatus DEFAULT_STATUS = HttpStatus.OK;

    private StatusLine statusLine;
    private HttpHeaders headers;
    private byte[] body;
    private List<Cookie> cookies;

    public HttpResponse() {
        this.statusLine = new StatusLine(DEFAULT_PROTOCOL_VERSION, DEFAULT_STATUS);
        this.headers = new HttpHeaders();
        this.cookies = new ArrayList<>();
    }

    public void setStatus(HttpStatus httpStatus) {
        statusLine.update(httpStatus);
    }

    public void addHeader(String headerName, String headerValue) {
        this.headers.add(headerName, headerValue);
    }

    public void setBody(byte[] body, ContentType contentType) {
        this.body = body;
        headers.setContentType(contentType.getContentType());
        headers.setContentLength(String.valueOf(body.length));
    }

    public void methodNotAllowed() {
        setStatus(HttpStatus.METHOD_NOT_ALLOWED);
        headers.setContentType("test/html");
        this.body = "<h1>405 Try another method!</h1>".getBytes();
    }

    public void notFound() {
        setStatus(HttpStatus.NOT_FOUND);
    }

    public void send(DataOutputStream dos) throws IOException {
        dos.writeBytes(statusLine.getStatusLineString() + System.lineSeparator());
        dos.writeBytes(headers.getHttpHeaderString() + System.lineSeparator());
        dos.writeBytes(System.lineSeparator());
        if (Objects.nonNull(body) && body.length != 0) {
            dos.write(body, 0, body.length);
        }
        dos.flush();
    }

    public HttpStatus getStatus() {
        return statusLine.getStatus();
    }

    public String getHeader(String name) {
        return headers.getHeader(name);
    }

    public void setCookie(String value) {
        headers.setCookie(value);
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void internalServerError() {
        setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        headers.setContentType("test/html");
        this.body = "<h1>500 Internal Server Error!</h1>".getBytes();
    }
}
