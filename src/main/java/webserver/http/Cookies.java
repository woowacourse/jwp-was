package webserver.http;

import webserver.http.utils.HttpUtils;
import webserver.http.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Cookies {
    public static final String JSESSIONID = "JSESSIONID";
    public static final String DELIMITER = "; ";
    public static final String DELIMITER_PAIR = "=";

    private final Map<String, Cookie> cookies;

    public Cookies() {
        this.cookies = new HashMap<>();
    }

    public Cookies(final String cookies) {
        this.cookies = parseCookies(cookies);
    }

    private static Map<String, Cookie> parseCookies(final String cookieText) {
        if (StringUtils.isEmpty(cookieText)) {
            return new HashMap<>();
        }
        return Arrays.stream(cookieText.split(DELIMITER))
                .map(cookie -> HttpUtils.parseKeyValue(cookie, DELIMITER_PAIR))
                .map(pair -> new Cookie(pair.getKey(), pair.getValue()))
                .collect(Collectors.toMap(Cookie::getName, cookie -> cookie));
    }

    public Cookie get(final String name) {
        return cookies.get(name);
    }

    public void add(final Cookie cookie) {
        cookies.put(cookie.getName(), cookie);
    }

    public int size() {
        return cookies.size();
    }

    public Set<String> keySet() {
        return cookies.keySet();
    }

    public Collection<Cookie> values() {
        return cookies.values();
    }

    public Set<Map.Entry<String, Cookie>> entrySet() {
        return cookies.entrySet();
    }

    public boolean contains(final String name) {
        return cookies.containsKey(name);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public String getSessionId() {
        final Cookie cookie = cookies.get(JSESSIONID);
        return cookie == null ? null : cookie.getValue();
    }
}
