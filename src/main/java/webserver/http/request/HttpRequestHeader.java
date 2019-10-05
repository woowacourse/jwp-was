package webserver.http.request;

import java.util.Objects;

import webserver.http.Header;

import static webserver.http.request.HttpRequest.HEADER_REQUEST_COOKIE;

public class HttpRequestHeader {
    private final Header header;

    public HttpRequestHeader(final Header header) {
        this.header = header;
    }

    public String getRequestElement(String elementKey) {
        return header.get(elementKey);
    }

    public boolean isCookieValue(String cookieKey) {
        String cookie = getRequestElement(HEADER_REQUEST_COOKIE);

        if (Objects.nonNull(cookie) && cookie.contains(cookieKey)) {
            return true;
        }
        return false;
    }
}
