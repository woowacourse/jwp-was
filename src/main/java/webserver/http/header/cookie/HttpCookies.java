package webserver.http.header.cookie;

import exception.InvalidCookieException;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpCookies {
    private static final String COOKIE_VALUE_DELIMITER = ";";

    private final List<HttpCookie> cookies;

    private HttpCookies(List<HttpCookie> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookies from(String cookieValue) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidCookieException(cookieValue));

        String[] tokens = cookieValue.split(COOKIE_VALUE_DELIMITER);
        List<HttpCookie> cookies = Arrays.stream(tokens)
                .map(HttpCookie::from)
                .collect(Collectors.toList());

        return new HttpCookies(cookies);
    }

    public static HttpCookies empty() {
        return new HttpCookies(new ArrayList<>());
    }

    public Optional<String> getCookieValue(String cookieName) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidCookieException(cookieName));

        return this.cookies.stream()
                .filter(httpCookie -> httpCookie.hasSameName(cookieName))
                .map(HttpCookie::getValue)
                .findFirst();
    }

    public void addCookie(HttpCookie httpCookie) {
        this.cookies.add(httpCookie);
    }

    public List<HttpCookie> getCookies() {
        return cookies;
    }
}
