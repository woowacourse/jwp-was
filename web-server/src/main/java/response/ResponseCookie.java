package response;

import cookie.Cookie;

class ResponseCookie {

    private Cookie cookie;
    private String path;

    ResponseCookie(String name, String value, String path) {
        cookie = new Cookie(name, value);
        this.path = path;
    }

    String toHttpResponseHeaderValueFormat() {
        // <cookie-name>=<cookie-value>; Path=<path-value>
        return cookie.toFormat() + "; Path=" + path;
    }

    String getName() {
        return cookie.getName();
    }

    String getValue() {
        return cookie.getValue();
    }
}
