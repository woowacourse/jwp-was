package webserver.http;

import webserver.http.utils.HttpUtils;
import webserver.http.utils.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Cookies {
    public static final String JSESSIONID = "JSESSIONID";
    static final String DELIMITER = "; ";
    static final String DELIMITER_PAIR = "=";

    private final Map<String, Cookie> cookies;

    public Cookies() {
        this.cookies = new HashMap<>();
    }

    public Cookies(final String cookies) {
        this.cookies = parseCookies(cookies);
    }

    // todo 유효하지 않은 값 예외처리도 해줘야할까? ex) login/true
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

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean contains(final String name) {
        return cookies.containsKey(name);
    }
}
