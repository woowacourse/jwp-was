package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import model.general.Cookie;
import model.general.Header;
import model.general.Headers;

public class CookieUtils {

    private static final String COOKIE_SEPARATOR = ";";

    public static List<Cookie> generateCookies(Headers headers) {
        String rawCookies = headers.getValue(Header.COOKIE);
        if (Objects.isNull(rawCookies)) {
            return Collections.emptyList();
        }

        List<Cookie> cookies = new ArrayList<>();
        for (String rawCookie : rawCookies.split(COOKIE_SEPARATOR)) {
            cookies.add(Cookie.of(rawCookie));
        }

        return cookies;
    }
}
