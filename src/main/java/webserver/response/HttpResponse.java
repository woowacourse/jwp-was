package webserver.response;

import webserver.common.HttpStatus;
import webserver.request.HttpRequest;

import java.util.Objects;

public class HttpResponse {
    public static final String HEAD_FIELD_SET_COOKIE = "Set-Cookie";
    public static final String EQUAL_SIGN = "=";
    private static final String HEADER_FIELD_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_FIELD_CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_FIELD_LOCATION = "Location";
    private static final String HTTP_PROTOCOL = "http://";
    private static final String HEADER_FIELD_HOST = "Host";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String SEMICOLON = ";";
    private static final String DEFAULT_COOKIE_PATH = "Path=/";
    private static final String BLANK = " ";

    private ResponseStatusLine responseStatusLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    public HttpResponse() {
        this.responseHeader = new ResponseHeader();
        this.responseBody = new ResponseBody();
    }

    public boolean setBody(byte[] body) {
        return this.responseBody.setBody(body);
    }

    public void setStatusLine(HttpRequest httpRequest, HttpStatus httpStatus) {
        responseStatusLine = ResponseStatusLine.of(httpRequest, httpStatus);
    }

    public boolean setHeader(String key, String value) {
        return responseHeader.addAttribute(key, value);
    }

    public void addCookie(String key, String value) {
        setHeader(HEAD_FIELD_SET_COOKIE, key + EQUAL_SIGN + value + SEMICOLON + BLANK + DEFAULT_COOKIE_PATH);
    }

    public String getSessionId() {
        return responseHeader.getSessionId();
    }

    public void sendRedirect(HttpRequest httpRequest, String path) {
        setStatusLine(httpRequest, HttpStatus.FOUND);
        if (path.startsWith(REDIRECT_PREFIX)) {
            setHeader(HEADER_FIELD_LOCATION, HTTP_PROTOCOL + httpRequest.getHeaderFieldValue(HEADER_FIELD_HOST)
                    + path.substring(REDIRECT_PREFIX.length()));
            return;
        }
        setHeader(HEADER_FIELD_LOCATION, HTTP_PROTOCOL + httpRequest.getHeaderFieldValue(HEADER_FIELD_HOST) + path);
    }

    public void forward(HttpRequest httpRequest, byte[] file, String contentType) {
        setStatusLine(httpRequest, HttpStatus.OK);
        setHeader(HEADER_FIELD_CONTENT_TYPE, contentType);
        setHeader(HEADER_FIELD_CONTENT_LENGTH, String.valueOf(file.length));
        setBody(file);
    }

    public String responseLine() {
        return responseStatusLine.response();
    }

    public String responseHeader() {
        return responseHeader.response();
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return Objects.equals(responseStatusLine, that.responseStatusLine) &&
                Objects.equals(responseHeader, that.responseHeader) &&
                Objects.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseStatusLine, responseHeader, responseBody);
    }
}
