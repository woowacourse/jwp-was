package domain.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import exception.CookieNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import servlet.Cookie;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Cookies {

    private static final Cookies EMPTY_COOKIES = new Cookies(new HashMap<>());

    private static final String COOKIES_DELIMITER = ";";

    private final Map<String, Cookie> cookies;

    public static Cookies from(String cookies) {
        if (Objects.isNull(cookies) || cookies.isEmpty()) {
            return EMPTY_COOKIES;
        }

        Map<String, Cookie> cookieMap = new HashMap<>();
        for (String cookie : cookies.split(COOKIES_DELIMITER)) {
            Cookie parsedCookie = Cookie.parseCookie(cookie.trim());
            cookieMap.put(parsedCookie.getKey(), parsedCookie);
        }

        return new Cookies(cookieMap);
    }

    public Cookie findCookie(String key) {
        if (cookies.containsKey(key)) {
            return cookies.get(key);
        }
        throw new CookieNotFoundException(key);
    }
}
