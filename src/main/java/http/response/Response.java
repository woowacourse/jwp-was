package http.response;

import enumtype.MediaType;
import http.Cookie;
import http.support.HttpStatus;
import utils.ExtractInformationUtils;
import utils.FileIoUtils;

import java.util.*;

public class Response {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final String LOCATION_FORMAT = "%s\r\n";
    private static final String CONTENT_TYPE_FORMAT = "%s;charset=utf-8\r\n";
    private static final String CONTENT_LENGTH_FORMAT = "%d\r\n";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String COOKIE_FORMAT = "%s=%s; Path=%s";

    private Map<String, String> header;
    private HttpStatus httpStatus;
    private List<Cookie> cookies;
    private String mimeType;
    private byte[] body;

    public Response() {
        header = new LinkedHashMap<>();
        cookies = new ArrayList<>();
    }

    public void forward(String location, HttpStatus httpStatus) {
        setMimeType(location);
        setHttpStatus(httpStatus);
        addHeader(CONTENT_TYPE, String.format(CONTENT_TYPE_FORMAT, mimeType));
        addHeader(CONTENT_LENGTH, String.format(CONTENT_LENGTH_FORMAT, body.length));

        addCookieHeader();
    }

    private void setMimeType(String location) {
        if (Objects.isNull(body)) {
            this.body = FileIoUtils.loadFileFromClasspath(location);
            mimeType = MediaType.of(ExtractInformationUtils.extractExtension(location)).getMediaType();
        }
    }

    public void sendRedirect(String location, HttpStatus httpStatus) {
        setHttpStatus(httpStatus);
        addHeader(LOCATION, String.format(LOCATION_FORMAT, location));
        addCookieHeader();
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public byte[] getBody() {
        return body;
    }

    public Set<String> getKeySet() {
        return header.keySet();
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public void notfound() {
        setHttpStatus(HttpStatus.NOT_FOUND);
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    private void addHeader(String key, String value) {
        header.put(key, value);
    }

    private void addCookieHeader() {
        for (Cookie cookie : cookies) {
            addHeader(SET_COOKIE,
                    String.format(COOKIE_FORMAT, cookie.getName(), cookie.getValue(), cookie.getPath()));
        }
    }
}
