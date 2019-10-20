package http.response;

import http.HttpHeaders;
import http.HttpVersion;
import http.MediaType;
import http.session.Cookie;

import static http.HttpHeaders.*;
import static http.response.HttpStatus.*;

public class HttpResponse {
    public static final String CRLF = "\r\n";
    private static final HttpStatus DEFAULT = OK;

    private HttpVersion version;
    private HttpStatus status;
    private HttpHeaders headers;
    private byte[] body;
    private Cookie cookie;

    public static HttpResponse of(HttpVersion version) {
        return new HttpResponse(version, new HttpHeaders());
    }

    private HttpResponse(HttpVersion version, HttpHeaders httpHeaders) {
        this.version = version;
        this.status = DEFAULT;
        this.headers = httpHeaders;
        cookie = Cookie.getEmptyCookie();
    }

    public void redirect(String location) {
        setStatus(FOUND);
        addHeader(LOCATION, location);
    }

    public void sendError() {
        setStatus(INTERNAL_SERVER_ERROR);
    }

    private void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public boolean hasBody() {
        return body != null;
    }

    public void addCookie(String key, String value) {
        cookie.addAttribute(key, value);
    }

    public String getMessageHeader() {
        if (!cookie.isEmpty()) {
            headers.put(SET_COOKIE, createSetCookieMessage());
        }

        return version + " " + status.getMessage() + CRLF
                + headers.toString();
    }

    private String createSetCookieMessage() {
        StringBuilder sb = new StringBuilder();

        for (String cookieName : cookie.getAttributes().keySet()) {
            sb.append(cookieName).append("=")
                    .append(cookie.getAttribute(cookieName)).append("; ");
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

    public String getCookieValue(String attributeName) {
        return cookie.getAttribute(attributeName);
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
