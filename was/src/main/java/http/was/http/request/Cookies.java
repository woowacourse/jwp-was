package http.was.http.request;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {
    private static final String COOKIES_DELIMITER = "; ";
    private static final String COOKIE_DELIMITER = "=";

    private final Map<String, Cookie> cookies = new HashMap<>();

    public Cookies() {
    }

    public Cookies(String cookies) {
        if (cookies == null) {
            return;
        }
        String[] tokens = cookies.split(COOKIES_DELIMITER);
        for (String token : tokens) {
            String[] value = token.split(COOKIE_DELIMITER);
            Cookie cookie = new Cookie(value[0], value[1]);
            addCookie(cookie);
        }
    }

    public String getCookieValue(String key) {
        Cookie cookie = cookies.get(key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return "";
    }

    public void addCookie(Cookie cookie) {
        cookies.put(cookie.getName(), cookie);
    }

    @Override
    public String toString() {
        return cookies.values()
                .stream()
                .map(Cookie::toString)
                .collect(Collectors.joining("; "));
    }
}
