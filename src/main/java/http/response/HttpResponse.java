package http.response;

import http.HttpHeaders;
import http.HttpVersion;
import http.MediaType;
import http.session.Cookie;

import java.util.ArrayList;
import java.util.List;

import static http.HttpHeaders.*;
import static http.response.HttpStatus.FOUND;
import static http.response.HttpStatus.OK;

public class HttpResponse {
    public static final String CRLF = "\r\n";

    private HttpVersion version;
    private HttpStatus status;
    private HttpHeaders headers;
    private byte[] body;
    private List<Cookie> cookies;

    public static HttpResponse of(HttpVersion version) {
        return new HttpResponse(version, new HttpHeaders());
    }

    private HttpResponse(HttpVersion version, HttpHeaders httpHeaders) {
        this.version = version;
        this.status = OK;
        this.headers = httpHeaders;
        cookies = new ArrayList<>();
    }

    public void redirect(String location) {
        setStatus(FOUND);
        addHeader(LOCATION, location);
    }

    private void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public boolean hasBody() {
        return body != null;
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public String getMessageHeader() {
        if (!cookies.isEmpty()) {
            headers.put(SET_COOKIE, createSetCookieMessage());
        }

        return version + " " + status.getMessage() + CRLF
                + headers.toString();
    }

    private String createSetCookieMessage() {
        StringBuilder sb = new StringBuilder();

        for (Cookie cookie : cookies) {
            sb.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
        }
        return sb.toString();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getHeader(String fieldName) {
        return headers.getHeader(fieldName);
    }

    public byte[] getBody() {
        return body;
    }

    public List<Cookie> getCookies() {
        return cookies;
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
