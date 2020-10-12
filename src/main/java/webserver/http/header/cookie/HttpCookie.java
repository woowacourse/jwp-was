package webserver.http.header.cookie;

import exception.InvalidCookieException;
import utils.StringUtils;

import java.util.Objects;

public class HttpCookie {
    private static final String COOKIE_OPTION_DELIMITER = ";";

    private final String name;
    private final String value;
    private final HttpCookieOption httpCookieOption;

    private HttpCookie(String name, String value, HttpCookieOption httpCookieOption) {
        this.name = name;
        this.value = value;
        this.httpCookieOption = httpCookieOption;
    }

    public static HttpCookie of(String name, String value, HttpCookieOption httpCookieOption) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidCookieException(name, value));

        String cookieName = name.trim();
        String cookieValue = value.trim();
        if (cookieName.isEmpty() || cookieValue.isEmpty()) {
            throw new InvalidCookieException(name, value);
        }

        return new HttpCookie(cookieName, cookieValue, httpCookieOption);
    }

    public static HttpCookie of(String name, String value) {
        return of(name, value, null);
    }

    public String toHttpMessage() {
        if (Objects.isNull(this.httpCookieOption)) {
            return this.name + "=" + this.value;
        }

        return this.name + "=" + this.value + COOKIE_OPTION_DELIMITER + this.httpCookieOption.toHttpMessage();
    }
}
