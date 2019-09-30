package webserver.http.session;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    private static final String COOKIE_LINE_REGEX = "; ";
    private static final String COOKIE_REGEX = "=";
    private static final String NONE_VALUE = "";

    private final Map<String, String> cookies;

    public Cookie() {
        cookies = new HashMap<>();
    }

    public Cookie(String cookieLine) {
        this.cookies = parseCookies(cookieLine);
    }

    private Map<String, String> parseCookies(String cookieLine) {
        if (StringUtils.isEmpty(cookieLine)) {
            return new HashMap<>();
        }
        return extractCookies(cookieLine.split(COOKIE_LINE_REGEX));
    }

    private Map<String, String> extractCookies(String[] attribute) {
        return Arrays.stream(attribute)
                .map(cookie -> cookie.split(COOKIE_REGEX))
                .collect(Collectors.toMap(index -> index[0],
                        index -> index.length > 1 ? index[1] : NONE_VALUE));
    }

    public String getCookies(String key) {
        return cookies.get(key);
    }
}