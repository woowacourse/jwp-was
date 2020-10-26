package web.server.domain.request;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.common.Cookie;
import web.server.domain.exception.CookieNotFoundException;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Cookies {

    private static final String COOKIES_DELIMITER = ";";

    private final Map<String, Cookie> cookies;

    public static Cookies from(String cookies) {
        Map<String, Cookie> cookieMap = Arrays.stream(cookies.split(COOKIES_DELIMITER))
            .map(String::trim)
            .map(Cookie::parseCookie)
            .collect(toMap(Cookie::getKey, value -> value));

        return new Cookies(cookieMap);
    }

    public Cookie findCookie(String key) {
        if (cookies.containsKey(key)) {
            return cookies.get(key);
        }
        throw new CookieNotFoundException(key);
    }
}
