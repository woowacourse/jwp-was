package webserver.http;

import webserver.http.request.Pair;
import webserver.http.utils.HttpUtils;
import webserver.http.utils.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Cookies {
    private static final String DELIMITER_COOKIE = "; ";
    private static final String DELIMITER_COOKIE_PAIR = "=";

    private final Map<String, String> map;

    public Cookies() {
        this.map = new HashMap<>();
    }

    public Cookies(final String cookies) {
        this.map = parseCookie(cookies);
    }

    // todo 유효하지 않은 값 예외처리도 해줘야할까? ex) login/true
    private static Map<String, String> parseCookie(final String cookieText) {
        if (StringUtils.isEmpty(cookieText)) {
            return new HashMap<>();
        }

        return Arrays.stream(cookieText.split(DELIMITER_COOKIE))
                .map(cookie -> HttpUtils.parseKeyValue(cookie, DELIMITER_COOKIE_PAIR))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    // todo String  (+)2개가 나을까? String.format이 나을까?
    public String getAllCookiesAsString() {
        return map.keySet().stream()
                .map(key -> key + DELIMITER_COOKIE_PAIR + map.get(key))
                .collect(Collectors.joining(DELIMITER_COOKIE));
    }

    public String get(final String key) {
        return map.get(key);
    }

    public void put(final String key, final String value) {
        map.put(key, value);
    }

    public int size() {
        return map.size();
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean isNotEmpty(){
        return !isEmpty();
    }
}
