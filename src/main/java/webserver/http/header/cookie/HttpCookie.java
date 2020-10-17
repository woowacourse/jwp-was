package webserver.http.header.cookie;

import exception.InvalidCookieException;
import utils.StringUtils;

import java.util.Objects;

public class HttpCookie {
    private static final String COOKIE_OPTION_DELIMITER = ";";
    private static final String EQUALS_SIGN = "=";

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

    public static HttpCookie from(String cookieToken) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidCookieException(cookieToken));

        String[] tokens = cookieToken.split(EQUALS_SIGN);
        if (tokens.length != 2) {
            throw new InvalidCookieException(cookieToken);
        }

        return of(tokens[0], tokens[1]);
    }

    public String toHttpMessage() {
        if (Objects.isNull(this.httpCookieOption)) {
            return this.name + EQUALS_SIGN + this.value;
        }

        return this.name + EQUALS_SIGN + this.value + COOKIE_OPTION_DELIMITER + this.httpCookieOption.toHttpMessage();
    }

    public boolean hasSameName(String name) {
        return this.name.equals(name);
    }

    public String getValue() {
        return value;
    }
}
